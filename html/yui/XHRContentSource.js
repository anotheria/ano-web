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
	};
	var CS = YAHOO.anoweb.XHRContentSource;
	CS.prototype = {
		
		uri : null,
		method: 'GET',
		container : null,
		
		update: function(){
			var containerEl = document.getElementById(this.container);
			var handleSuccess = function(o){
				if(o.responseText !== undefined){
					containerEl.innerHTML = o.responseText;
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
			};
			YAHOO.util.Connect.asyncRequest(this.method, this.uri, callback);
		}
	};
})();

YAHOO.register('anoweb.XHRContentSource', YAHOO.anoweb.XHRContentSource, {version: "0.99", build: '11'});

