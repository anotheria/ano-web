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
			oConfig.e = oConfig.panel || Dom.generateId();
			
			this._config = oConfig;
		    
//		    this.resizer.on("resize", function(args) {
//                var panelHeight = args.height;
//                this.cfg.setProperty("height", panelHeight + "px");
//            }, this, true);

//			this.anoPanel.subscripe('contentChanged', function(){
//				this.fireContentChanged();
//			});
			
			this.on('click', function (event) {
				Event.preventDefault(event);
				var anoPanel = new YAHOO.anoweb.widget.AnoPanel(this._config.container, this._config);
				anoPanel.render(this._config.container);
				//PanelLink.manager.register(anoPanel);
				//alert('Container: ' + this.container);
//				this.resizer = new YAHOO.util.Resize(this.get('id'), {
//					handles: ["br"],
//					autoRatio: false,
//					minWidth: 300,
//					minHeight: 100,
//					status: false 
//				});
				anoPanel.show();
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

