YAHOO.namespace('YAHOO.anoweb.widget');

(function () {
	var Event = YAHOO.util.Event,
		DOM = YAHOO.util.Dom,
		Element = YAHOO.util.Element;
	
    YAHOO.anoweb.widget.DatePicker = function (inputElement, containerElement, cfg) {
    	this.inputElement = DOM.get(inputElement);
    	this.containerElement = containerElement;
    	this.init();
    };
    
    var DP = YAHOO.anoweb.widget.DatePicker;
	
    DP.prototype = {
    		inputElement: null,
    		containerElement: null,
    		overlay: null,
    	    calendar: null,
    		
    	    init: function(){
    			this.initOverlay();
    			this.initCalendar();
    			this.initEvents();
    			this.hide();
    		},
    		
    		initOverlay: function (){
    			try{
    			var containerEl = document.createElement('div');
    			containerEl.setAttribute('id',"ano-" + DOM.generateId());
    			DOM.setStyle(containerEl,"position","absolute");
    			DOM.insertAfter(containerEl,this.inputElement);
    			var region = DOM.getRegion(this.inputElement);
    			DOM.setX(containerEl,region.left);
    			DOM.setY(containerEl,region.bottom);
    			this.containerElement = containerEl;
    			
    			this.overlay = new YAHOO.widget.Overlay(containerEl, { visible:false} );
    			this.overlay.setBody("&#32;");
    			this.overlay.body.id = DOM.generateId();
    			this.overlay.render();
    			}catch(e){
    				alert("overlay: " + e);
    			}
    		},
    		
			initCalendar: function () {
    			try{
    			this.calendar = new YAHOO.widget.Calendar(this.overlay.body.id,{navigator:true});
				this.calendar.render();
		
				this.calendar.selectEvent.subscribe(function (p_sType, p_aArgs) {
					var aDate;
					if (p_aArgs) {
						aDate = p_aArgs[0][0];
						this.inputElement.value = aDate[0] + "-" + aDate[1] + "-" + aDate[2];
					}
					this.hide();
				},this,true);
    			}catch(e){
    				alert("calendar: " + e);
    			}
			},

		    initEvents: function(){
				Event.addListener(this.inputElement,'click',this.show,this,true);
		    	Event.addListener(document,'mousedown',
    			function(e){
		    		var target = Event.getTarget(e);
		    		//alert('Event Mousedown: showed ' + this.showed + ", ancestor " + DOM.isAncestor(this.overlay.element,target));
		    		if(this.showed && !DOM.isAncestor(this.overlay.element,target))
		    			this.hide();
		    	}
		    	,this,true);
		    },
		    
			show: function(){
		    	//DOM.setStyle(this.overlay.element,'display','block');
		    	this.overlay.bringToTop();
		    	if(this.showed)
		    		return;
		    	this.showed = true;
		    	this.calendar.show();
		    	this.overlay.show();
		    },
		    
		    hide: function(){
		    	this.showed = false;
		    	this.calendar.hide();
		    	this.overlay.hide();
		    	//DOM.setStyle(this.overlay.element,'display','none');
		    }
    }
})();

YAHOO.register('anoweb.widget.DatePicker', YAHOO.anoweb.widget.DatePicker, {version: "0.99", build: '11'});

