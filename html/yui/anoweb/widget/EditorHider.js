YAHOO.namespace('YAHOO.anoweb.widget');
(function() {
	var Dom = YAHOO.util.Dom;
	YAHOO.anoweb.widget.HidableEditor = function(){
	};
	var HidableEditor = YAHOO.anoweb.widget.HidableEditor;
	
	HidableEditor.prototype.hidden = false;
	
	HidableEditor.prototype.hideEditor = function(){
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
		this.hidden = true;
	};
	HidableEditor.prototype.showEditor = function(){
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
		this.hidden = false;
	}
	
	HidableEditor.prototype.isHidden = function(){
		return this.hidden;
	}
	
	HidableEditor.prototype.saveHtmlIfShowed = function(){
		if(!this.hidden)
			this.saveHTML();
			
	}
	
	YAHOO.lang.augment(YAHOO.widget.Editor, HidableEditor);
})();


YAHOO.register('YAHOO.anoweb.widget.HidableEditor', YAHOO.anoweb.widget.HidableEditor, {version: "0.99", build: '11'});

