YAHOO.namespace('anoweb');
(function(){

	var lang	= YAHOO.lang,
	    util	= YAHOO.util,
	    Event		= util.Event,
		Element = util.Element,
		Dom		= util.Dom;
	
	var XHRLink = function(linkElement, oConfig){
		XHRLink.superclass.constructor.call(this, linkElement, oConfig);
	};
	
	YAHOO.anoweb.XHRLink = XHRLink;
	lang.extend(XHRLink,YAHOO.util.Element,{
		_xhrContentSource: null,
		init: function(el, oConfig) {
			XHRLink.superclass.init.call(this, el, oConfig);
			if(!oConfig.uri)
				oConfig.uri = this.get('href');
			_xhrContentSource = new YAHOO.anoweb.XHRContentSource(oConfig);
			if(!oConfig.uri);
			this.on('click', function (event) {
				Event.preventDefault(event);
				_xhrContentSource.update();
			});
		}
	});
	
//	XHRLink.prototype = {			
//	};
	

})();

YAHOO.register('anoweb.XHRLink', YAHOO.anoweb.XHRLink, {version: "0.99", build: '11'});

