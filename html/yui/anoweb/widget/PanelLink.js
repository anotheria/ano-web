YAHOO.namespace('YAHOO.anoweb.widget');
(function(){

	var lang	= YAHOO.lang,
	    util	= YAHOO.util,
	    Event	= util.Event,
		Element = util.Element,
		Dom		= util.Dom;
	
	YAHOO.anoweb.widget.PanelLink = function(linkElement, oConfig){
		YAHOO.anoweb.widget.PanelLink.superclass.constructor.call(this, linkElement, oConfig);
		this.createEvent('contentChanged');
	};
	
	var PanelLink = YAHOO.anoweb.widget.PanelLink;
		
	lang.extend(PanelLink, Element,{		
		getAnoPanel: function(){
			return anoPanel;
		},
		setAnoPanel: function(panel){
			this.anoPanel = panel
		},
		fireContentChanged: function(){
			this.fireEvent('contentChanged');
		},	
		init: function(el, oConfig) {
			PanelLink.superclass.init.call(this, el, oConfig);
			
			
			oConfig.dataSrc = oConfig.dataSrc || this.get('href');
			oConfig.panelElement = oConfig.panelElement || Dom.generateId();
			
			this._config = oConfig;
		    

//			this.anoPanel.subscripe('contentChanged', function(){
//				this.fireContentChanged();
//			});
			
			this._rendered = false;
			this.on('click', function (event) {
				Event.preventDefault(event);
				if(!this._rendered){
					this._rendered = true;
					this.anoPanel = new YAHOO.anoweb.widget.AnoPanel(this._config.panelElement, this._config);
					this.anoPanel.render(this._config.container);
					this.anoPanel.moveTo(window.pageXOffset + event.clientX, window.pageYOffset + event.clientY);
				}
				this.anoPanel.show();
				this.anoPanel.focus();
			}, this, true);

		} 
		
	});
	
	PanelLink.manager = new YAHOO.widget.OverlayManager();
	
	PanelLink.wrappAll = function(tag, root, container, onContentChangedHanler){
		YAHOO.util.Dom.getElementsBy(
				function(){return true},
				tag,
				root,
				function(linkElement){
					var panelLink = new PanelLink(linkElement,{container:container});
//					if(onContentChanged)
//						panelLink.addListener('contentChanged', onContentChanged);
				}
		);
	};

})();

YAHOO.register('YAHOO.anoweb.widget.PanelLink', YAHOO.anoweb.widget.PanelLink, {version: "0.99", build: '11'});

