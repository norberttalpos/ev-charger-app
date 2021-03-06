import SockJS from "sockjs-client";
import Stomp from "webstomp-client";
import {serverprefix} from "@/main";

export const WebsocketClient = {
	methods: {
		connect(type, room) {
			this.socket = new SockJS(`${serverprefix}/socket`);
			this.stompClient = Stomp.over(this.socket);

			this.stompClient.connect({Authorization: "Bearer " + localStorage.getItem('accessToken')},
				() => {
					this.connected = true;

					this.stompClient.subscribe(`/topic/${type}/${room}`, tick => {
						const receivedMessageBody = JSON.parse(tick.body);
						this.onWebsocketEvent(receivedMessageBody);
					});
				},

				() => {
					this.connected = false;
				}
			);
		},
		disconnect() {
			if (this.stompClient) {
				this.stompClient.disconnect();
			}
			this.connected = false;
		},
	},
}