(function() {
	var DOM = YAHOO.util.Dom,
		Event = YAHOO.util.Event,
		Lang = YAHOO.lang;
	
	var FormUtils = YAHOO.anoweb.util.FormUtils;
	
	FormUtils = {
		reflectInput: function(input, refl, recursively) {
            if(recursively && Lang.isArray(refl)){
            	for(i = 0; i < refl.length; i++){
            		DOM.batch(refl, FormUtils._reflectInput, refl[i]);
            	}
            }
            DOM.batch(refl, FormUtils._reflectInput, input);
        },
		
		
		_reflectInput: function(refl, input){
			input = DOM.get(input);
			refl = DOM.get(refl);
			
			if(input === refl)
				return;
			
			var copyValue = function(fromEl, toEl){
				if(fromEl.tagName == "INPUT" && toEl.tagName == "INPUT"){
					toEl.value = fromEl.value;
					return;
				}
				if(fromEl.tagName == "TEXTAREA" && toEl.tagName == "TEXTAREA"){
					toEl.value = fromEl.value;
					return;
				}
				if(fromEl.tagName == "SELECT" && toEl.tagName == "SELECT"){
					if(fromEl.selectedIndex >= 0)
					toEl.selectedIndex = fromEl.selectedIndex;
					return;
				}
			}
			
			copyValue(input, refl);
			
			Event.addListener(input,"change",function(event, refl){
				copyValue(this, refl);
			},refl,false);
		},
		syncInputs: function(input, syncInput, recursively){
			input = DOM.get(input);
			syncInput = DOM.get(syncInput);
			FormUtils.reflectInput(input,syncInput,recursively);
			FormUtils.reflectInput(syncInput,input,recursively);
		}
		
	}
	
})();
YAHOO.register('YAHOO.anoweb.util.FormUtils', YAHOO.anoweb.util.FormUtils, {version: "0.99", build: '11'});

