YAHOO.namespace('YAHOO.anoweb.widget');
(function() {
	var Dom = YAHOO.util.Dom;
	YAHOO.anoweb.widget.HidableEditor = function(){
	};
	var HidableEditor = YAHOO.anoweb.widget.HidableEditor;
	
	HidableEditor.prototype.turnedOff = false;
	
	HidableEditor.prototype.turnOff = function(){
		this.saveHTML();				
	
		var fc = this.get('element').previousSibling,
	    el = this.get('element');
	
		Dom.setStyle(fc, 'position', 'absolute');
		Dom.setStyle(fc, 'top', '-9999px');
		Dom.setStyle(fc, 'left', '-9999px');
		this.get('element_cont').removeClass('yui-editor-container');
		Dom.setStyle(el, 'visibility', 'visible');
		Dom.setStyle(el, 'top', '');
		Dom.setStyle(el, 'left', '');
		Dom.setStyle(el, 'position', 'static');
		this.turnedOff = true;
	};
	HidableEditor.prototype.turnOn = function(){
		var fc = this.get('element').previousSibling,
		el = this.get('element');
	
		Dom.setStyle(fc, 'position', 'static');
		Dom.setStyle(fc, 'top', '0');
		Dom.setStyle(fc, 'left', '0');
		Dom.setStyle(el, 'visibility', 'hidden');
		Dom.setStyle(el, 'top', '-9999px');
		Dom.setStyle(el, 'left', '-9999px');
		Dom.setStyle(el, 'position', 'absolute');
		this.get('element_cont').addClass('yui-editor-container');
		this._setDesignMode('on');
		this.setEditorHTML(this.get('textarea').value);
		this.turnedOff = false;
	}
	
	HidableEditor.prototype.isTurnedOff = function(){
		return this.turnedOff;
	}

	HidableEditor.prototype.toggle = function(){
		if(this.isTurnedOff())
			this.turnOn();
		else
			this.turnOff();
	}
	
	
//	HidableEditor.prototype.saveHtmlIfShowed = function(){
//		if(!this.turnedOff)
//			this.saveHTML();
//			
//	}
	
	YAHOO.lang.augment(YAHOO.widget.Editor, HidableEditor);
})();


YAHOO.register('YAHOO.anoweb.widget.HidableEditor', YAHOO.anoweb.widget.HidableEditor, {version: "0.99", build: '11'});

