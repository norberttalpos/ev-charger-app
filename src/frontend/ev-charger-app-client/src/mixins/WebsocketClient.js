import SockJS from "sockjs-client";
import Stomp from "webstomp-client";
import {serverprefix} from "@/main";

export const WebsocketClient = {
	data() {
		return {
			received_messages: [],
		}
	},
	methods: {
		connect() {
			this.socket = new SockJS(`${serverprefix}/socket`);
			this.stompClient = Stomp.over(this.socket);
			this.stompClient.connect(
				{Authorization: "Bearer " + localStorage.getItem('accessToken')},
				frame => {
					this.connected = true;
					console.log(frame);
					this.stompClient.subscribe("/topic/chargingStationToServer", tick => {
						console.log(tick);
						this.received_messages.push(JSON.parse(tick.body).content);
					});
				},
				error => {
					console.log(error);
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
	created() {
		this.connect()
	}
}