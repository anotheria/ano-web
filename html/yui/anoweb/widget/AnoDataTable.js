YAHOO.namespace('anoweb.widget');
(function(){
	
	var Lang = YAHOO.lang,
		DT = YAHOO.widget.DataTable;
	
	YAHOO.anoweb.widget.AnoDataTable = function(elContainer , oColumnSet , oDataSource , oConfigs) {
		YAHOO.anoweb.widget.AnoDataTable.superclass.constructor.call(this, elContainer , oColumnSet , oDataSource , oConfigs);
		this.createEvent('beforeRefresh');
		this.createEvent('refresh');
	};
	
	var ADT = YAHOO.anoweb.widget.AnoDataTable;
	Lang.extend(ADT, DT);
	
	ADT.prototype.refresh = function(oReq) {
		oReq = oReq || '';
		this.fireEvent('beforeRefresh');
		var oState = this.getState();
		var callback = {
				success : this.onDataReturnInitializeTable,
				failure : this.onDataReturnSetRows,
				argument : oState, // Pass along the new state to the callback
				scope : this
		};
		this._oDataSource.sendRequest(oReq, callback);
	 }; 
	 
	 ADT.prototype.update = function(interval, oReq){
		 //this.updateStop();
		 if(ADT.isValidUpdateInterval(interval))
			 _updating = Lang.later(interval,this,function(_oReq){
				 this.refresh(_oReq);
			 },oReq,true);
	 }
	 
	 ADT.prototype.updateStop = function(interval, oReq){
		 if(_updating){
			 _updating.cancel.call(this);
			 _updating = false;
		 }
	 }
    
	 ADT.isValidUpdateInterval = function(interval){
		 return true;
	 }
	 
	 ADT.prototype.isUpdate = function(){
		 return !(_updating === false);
	 }
	 
	 var _updating = false;
	 
    var log = function(message){
    	YAHOO.log(message, "info", "AnoDataTable");
    }
})();
YAHOO.register('anoweb.widget.AnoDataTable', YAHOO.anoweb.widget.AnoDataTable, {version: "0.99", build: '11'});

