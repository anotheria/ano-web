YAHOO.namespace('anoweb');
(function(){

	var lang	= YAHOO.lang,
	    util	= YAHOO.util,
	    Event	= util.Event,
		Element = util.Element,
		Dom		= util.Dom;
	
	var XHRLink = function(linkElement, oConfig){
		XHRLink.superclass.constructor.call(this, linkElement, oConfig);
		this.createEvent('contentChanged');
	};
	
	YAHOO.anoweb.XHRLink = XHRLink;
		
	lang.extend(XHRLink,YAHOO.util.Element,{		
		fireContentChanged: function(){
			this.fireEvent('contentChanged');
		},	
		init: function(el, oConfig) {
			XHRLink.superclass.init.call(this, el, oConfig);
			oConfig.uri = oConfig.uri || this.get('href');
			this._xhrContentSource = new YAHOO.anoweb.XHRContentSource(oConfig);
			this._xhrContentSource.addListener('contentChanged', function(scope, obj){
				obj.fireContentChanged();
			},this);
			
			this.on('click', function (event) {
				Event.preventDefault(event);
				this._xhrContentSource.update();
			});
		}
	});
	
	
	YAHOO.anoweb.XHRLink.wrappAll = function(tag, root, container, onContentChanged){
		YAHOO.util.Dom.getElementsBy(
				function(){return true},
				tag,
				root,
				function(a){
					var xhrLink = new YAHOO.anoweb.XHRLink(a,{container:container});
					if(onContentChanged)
						xhrLink.addListener('contentChanged', onContentChanged);
				}
		);
	};

})();

YAHOO.register('anoweb.XHRLink', YAHOO.anoweb.XHRLink, {version: "0.99", build: '11'});

