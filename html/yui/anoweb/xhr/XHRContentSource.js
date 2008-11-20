YAHOO.namespace('anoweb');
(function(){

	var lang	= YAHOO.lang,
	    util	= YAHOO.util,
	    Ev		= util.Event,
		Element = util.Element,
		Dom		= util.Dom;
	
	YAHOO.anoweb.XHRContentSource = function(oConfigs){
		if(oConfigs && (oConfigs.constructor == Object)) {
	        for(var sConfig in oConfigs) {
	            if(sConfig) {
	                this[sConfig] = oConfigs[sConfig];
	            }
	        }
		}
		this.createEvent('contentChanged');
	};
	
	var CS = YAHOO.anoweb.XHRContentSource;
	
	CS.prototype = {		
		uri : null,
		method: 'GET',
		container : null,	
		update: function(){
			var containerEl = Dom.get(this.container);
			var handleSuccess = function(o){
				if(o.responseText !== undefined){
					containerEl.innerHTML = o.responseText;
					this.fireEvent('contentChanged');
				}
			}
			var handleFailure = function(o){
				if(o.responseText !== undefined){
					containerEl.innerHTML = '<ul><li>Transaction id: ' + o.tId + '</li>';
					containerEl.innerHTML += '<li>HTTP status: ' + o.status + '</li>';
					containerEl.innerHTML += '<li>Status code message: ' + o.statusText + '</li></ul>';
				}
			}
			var callback =
			{
				success:handleSuccess,
				failure:handleFailure,
				scope:this
			};
			YAHOO.log('[XHRContentSource.update()]Sending asyncRequest: method ' + this.method + ', uri ' +  this.uri);
			YAHOO.util.Connect.asyncRequest(this.method, this.uri, callback);
		},
		toString: function(){
			var ret = '';
			ret += 'method ' + this.method;
			ret += ', ';
			ret += 'uri ' + this.uri;
			ret += ', ';
			ret += 'container ' + this.container;
			return ret;
		},
        addListener: function(type, callback) {
			this.subscribe.apply(this, arguments);
        }
	};
	
	YAHOO.lang.augmentProto(YAHOO.anoweb.XHRContentSource, YAHOO.util.EventProvider);

})();

YAHOO.register('anoweb.XHRContentSource', YAHOO.anoweb.XHRContentSource, {version: "0.99", build: '11'});

