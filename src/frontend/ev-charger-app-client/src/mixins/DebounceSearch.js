export const DebounceSearch = {
	methods: {
		debounceSearch(fun) {
			clearTimeout(this.debounce)
			this.debounce = setTimeout(fun, 600)
		}
	},
}