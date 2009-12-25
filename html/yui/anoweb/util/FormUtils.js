YAHOO.namespace('YAHOO.anoweb.util');
(function() {
	var DOM = YAHOO.util.Dom,
		Event = YAHOO.util.Event,
		Lang = YAHOO.lang;
	
	var FormUtils = {
		
		reflectInputs: function(input, refl, recursively) {
			
			input = DOM.get(input);
			refl = DOM.get(refl);
			       
			DOM.batch(refl, FormUtils._reflectInput, {input: input, recursively: recursively});
			
			if(recursively){
				if(Lang.isArray(refl)){
	            	for(i = 0; i < refl.length; i++){
	            		DOM.batch(input, FormUtils._reflectInput, {input: refl[i], recursively: recursively});
	            		DOM.batch(refl, FormUtils._reflectInput, {input: refl[i], recursively: recursively});
	            	}
	            }
				if(Lang.isObject(refl)){
					
				}
			}
        },
		
		
		_reflectInput: function(reflEl, args){
			var input = DOM.get(args.input),
				refl = DOM.get(reflEl),
				recursively = args.recursively;
			
			if(input === refl)
				return;
			
			var copyValue = function(fromEl, toEl){
				try{
					if(fromEl.tagName == "INPUT" && toEl.tagName == "INPUT"){
						if(fromEl.type=="checkbox"){
							toEl.checked = fromEl.checked;
							return toEl.checked;
						}
						toEl.value = fromEl.value;
						return toEl.value;
					}
					if(fromEl.tagName == "TEXTAREA" && toEl.tagName == "TEXTAREA"){
						toEl.value = fromEl.value;
						return toEl.value;
					}
					if(fromEl.tagName == "SELECT" && toEl.tagName == "SELECT"){
						if(fromEl.selectedIndex >= 0)
						toEl.selectedIndex = fromEl.selectedIndex;
						return toEl.selectedIndex;
					}
					
					if(fromEl.combobox && toEl.combobox){
						toEl.combobox.setSelection(fromEl.combobox.getSelection(), true);
						return toEl.combobox.getSelection();
					}
					
					if(fromEl instanceof tinymce.Editor && toEl instanceof tinymce.Editor ){
						toEl.setContent(fromEl.getContent());
						return toEl.getContent();
					}
					alert("FormUtils.copyValue. Uknown elemnts to copy: FROM ELEMENT" + fromEl.tagName + "#" + fromEl.id + 
							"\n TO ELEMENT " + toEl.tagName + "#" + toEl.id);
				}catch(e){
					alert("FormUtils.copyValue failure: " + e.message);
				}
			}
			
			var keepSyncOnInput = function(input, refl){
				try{
					if(input.combobox){
						input.combobox.onChange(function(event, args, refl){
							copyValue(this, refl);
						},refl);
						return;
					}
					if(DOM.hasClass(input,"richTextEditor")){
						input = tinyMCE.get(input.id);
						refl = tinyMCE.get(refl.id);
						input.onChange.add(function(editor, event){
							copyValue(editor, this);
						},refl);
						return;
					}
	
					Event.addListener(input,"change",function(event, refl){
						copyValue(this, refl);
					},refl,false);
				}catch(e){
					alert("FormUtils.keepSyncOnInput failure: " + e.message);
				}
			}
			
			copyValue(input, refl);
			keepSyncOnInput(input, refl);
			if(recursively)
				keepSyncOnInput(refl, input);
		},
	}
	
	YAHOO.anoweb.util.FormUtils = FormUtils;
})();

