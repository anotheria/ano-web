YAHOO.namespace('YAHOO.anoweb.widget');
(function(){
	
	var Lang = YAHOO.lang,
		Panel = YAHOO.widget.Panel;
	
    YAHOO.anoweb.widget.AnoPanel = function (el, userConfig) {
    	YAHOO.anoweb.widget.AnoPanel.superclass.constructor.call(this, el, userConfig);
    };
    
    DEFAULT_CONFIG = {
        "LOAD_METHOD": { 
	        key: "loadMethod", 
	        value: "GET",
	        validator: Lang.isString, 
	        supercedes: ["visible"] 
    	},
        "LOAD_CONTANT": { 
	        key: "loadContent", 
	        value: "Loading...", 
	        supercedes: ["visible"] 
    	},
        "DATA_SRC": { 
            key: "dataSrc",
            value: false,
            validator: Lang.isString, 
            supercedes: ["visible"] 
        },
        "RESIZABLE": { 
            key: "resizable",
            value: true,
            validator: Lang.isBoolean, 
            supercedes: ["visible"] 
        }    	
    };

    var AnoPanel = YAHOO.anoweb.widget.AnoPanel;
    
    function createBody(p_sType, p_aArgs) {
        if (!this.body) {
            this.setBody("&#160;");
        }
    }
    
    function createFooter(p_sType, p_aArgs) {
        if (!this.footer) {
            this.setFooter("&#160;");
        }
    }

    
    Lang.extend(AnoPanel, Panel, {
        init: function (el, userConfig) {
	        AnoPanel.superclass.init.call(this, el, userConfig);
	        this.beforeInitEvent.fire(AnoPanel);
	        this.subscribe("beforeRender", createBody);
	        this.subscribe("beforeRender", createFooter);
	        this.initEvent.fire(AnoPanel);
	    },
        initDefaultConfig: function () {
    		AnoPanel.superclass.initDefaultConfig.call(this);
	
		    this.cfg.addProperty(DEFAULT_CONFIG.LOAD_CONTANT.key, { 
		    	value: DEFAULT_CONFIG.LOAD_CONTANT.value,
		        supercedes: DEFAULT_CONFIG.LOAD_CONTANT.supercedes 
		    });
		    
		    this.cfg.addProperty(DEFAULT_CONFIG.LOAD_METHOD.key, { 
		    	value: DEFAULT_CONFIG.LOAD_METHOD.value,
		    	validator: DEFAULT_CONFIG.LOAD_METHOD.validator, 
		        supercedes: DEFAULT_CONFIG.LOAD_METHOD.supercedes 
		    });
		    
		    this.cfg.addProperty(DEFAULT_CONFIG.DATA_SRC.key, {
		    	value: DEFAULT_CONFIG.DATA_SRC.value,
		        validator: DEFAULT_CONFIG.DATA_SRC.validator, 
		        supercedes: DEFAULT_CONFIG.DATA_SRC.supercedes 
		    });
		    
		    this.cfg.addProperty(DEFAULT_CONFIG.RESIZABLE.key, {
		    	value: DEFAULT_CONFIG.RESIZABLE.value,
		        validator: DEFAULT_CONFIG.RESIZABLE.validator, 
		        supercedes: DEFAULT_CONFIG.RESIZABLE.supercedes 
		    });
		},
		
		dataConnection: null,
		
		_loading: false,
    	
    	loadHandler:  {
                success: function(o) {
            		this.setBody(o.responseText);
        		},
        		failure: function(o) {
        		}
		},
		
		render: function (appendToNode, moduleElement) {
			AnoPanel.superclass.render.apply(this, arguments);
			AnoPanel.manager.register(this);
			if(this.cfg.getProperty('resizable')){
				log("Creating resize for element: " + this.innerElement.id);
				this._resize = new YAHOO.util.Resize(this.innerElement, {
					handles: ["br"],
					autoRatio: false,
					minWidth: 300,
					minHeight: 100,
					maxWidth: 1024,
					status: false 
				});
				log("Resize created!");
				
//				this._resize.on("startResize", function(args) {
//	    		    if (this.cfg.getProperty("constraintoviewport")) {
//	                    var D = YAHOO.util.Dom;
//	                    var clientRegion = D.getClientRegion();
//	                    var elRegion = D.getRegion(this.element);
//	                    resize.set("maxWidth", clientRegion.right - elRegion.left - YAHOO.widget.Overlay.VIEWPORT_OFFSET);
//	                    resize.set("maxHeight", clientRegion.bottom - elRegion.top - YAHOO.widget.Overlay.VIEWPORT_OFFSET);
//		            } else {
//	                    resize.set("maxWidth", null);
//	                    resize.set("maxHeight", null);
//		        	}
//	            }, this, true);

			    this._resize.on("resize", function(args) {
		              var panelHeight = args.height;
		              this.cfg.setProperty("height", panelHeight + "px");
		          }, this, true);
			}
		},
		
		show: function () {
			if (this.cfg.getProperty('dataSrc') ) {
				this.setBody(this.cfg.getProperty('loadContent'));
				if ( !this._loading) {
					_dataConnect.call(this);
				}
			}
			return AnoPanel.superclass.show.call(this);
        }
    });
    
    var log = function(message){
    	YAHOO.log(message, "info", "AnoPanle");
    }
    
    var _dataConnect = function() {
        if (!YAHOO.util.Connect) {
            YAHOO.log('YAHOO.util.Connect dependency not met',
                    'error', 'Tab');
            return false;
        }

        this._loading = true; 
        this.dataConnection = YAHOO.util.Connect.asyncRequest(
            this.cfg.getProperty('loadMethod'),
            this.cfg.getProperty('dataSrc'), 
            {
                success: function(o) {
            		YAHOO.log('content loaded successfully', 'info', 'AnoPanel');
                    this.loadHandler.success.call(this, o);
                    //this.set('dataLoaded', true);
                    this.dataConnection = null;
                    this._loading = false;
                },
                failure: function(o) {
                    YAHOO.log('loading failed: ' + o.statusText, 'error', 'AnoPanel');
                    this.loadHandler.failure.call(this, o);
                    this.dataConnection = null;
                    this._loading = false;
                },
                scope: this
            }
        );
    };
    
    AnoPanel.manager = new YAHOO.widget.OverlayManager();
    
})();

YAHOO.register('YAHOO.anoweb.widget.AnoPanel', YAHOO.anoweb.widget.AnoPanel, {version: "0.99", build: '11'});

