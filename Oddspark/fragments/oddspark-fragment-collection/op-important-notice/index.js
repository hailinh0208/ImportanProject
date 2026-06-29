(function () {
	if (customElements.get('op-important-notice')) {
		return;
	}

	var link = document.createElement('link');
	link.rel = 'stylesheet';
	link.href = '/o/oddspark-important-notice/style.css';
	document.head.appendChild(link);

	var script = document.createElement('script');
	script.src = '/o/oddspark-important-notice/index.js';
	document.head.appendChild(script);
})();
