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

    
})();

YAHOO.register('YAHOO.anoweb.widget.AnoPanel', YAHOO.anoweb.widget.AnoPanel, {version: "0.99", build: '11'});

