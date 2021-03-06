YAHOO.namespace('anoweb.widget');
(function(){
	
	var Lang = YAHOO.lang,
		DT = YAHOO.widget.DataTable;
	
	YAHOO.anoweb.widget.AnoDataTable = function(elContainer , oColumnSet , oDataSource , oConfigs) {
		YAHOO.anoweb.widget.AnoDataTable.superclass.constructor.call(this, elContainer , oColumnSet , oDataSource , oConfigs);
		YAHOO.anoweb.widget.AnoDataTable.instancesCounter++;
		this.instanceId = YAHOO.anoweb.widget.AnoDataTable.instancesCounter;
		log("Instantiated with ID " + this.instanceId);
		this.createEvent('beforeRefresh');
		this.createEvent('refresh');
	};
	
	var ADT = YAHOO.anoweb.widget.AnoDataTable;
	Lang.extend(ADT, DT,{
		_handleDataReturnPayload : function (oRequest, oResponse, oPayload) {
		    oPayload = this.handleDataReturnPayload(oRequest, oResponse, oPayload);
		    if(oPayload) {
		        // Update pagination
		        var oPaginator = this.get('paginator');
		        if (oPaginator) {
		            // Update totalRecords
		            if(this.get("dynamicData")) {
		                if (lang.isNumber(oPayload.totalRecords)) {
		                    oPaginator.set('totalRecords',oPayload.totalRecords);
		                }
		            }
		            else {
		                oPaginator.set('totalRecords',this._oRecordSet.getLength());
		            }
		            // Update other paginator values
		            if (lang.isObject(oPayload.pagination)) {
		                oPaginator.set('rowsPerPage',oPayload.pagination.rowsPerPage);
		                oPaginator.set('recordOffset',oPayload.pagination.recordOffset);
		            }
		        }
	
		        // Update sorting
		        if (oPayload.sortedBy) {
		            this.set('sortedBy', oPayload.sortedBy);
		            var oColumn = oPayload.sortedBy.column;
		            if(!this.get("dynamicData") && oColumn && oColumn.sortable) {
		                this.sortColumn(oColumn, oPayload.sortedBy.dir);
		            }

		        }
		        // Backwards compatibility for sorting
		        else if (oPayload.sorting) {
		            this.set('sortedBy', oPayload.sorting);
		        }
		    }
		}
	});
	
	ADT.prototype.refresh = function(oReq) {
		oReq = oReq || '';
		if(Lang.isFunction(oReq))
			oReq = oReq.call(oReq);
		this.fireEvent('beforeRefresh');
		var oState = this.getState();
		var callback = {
				success : this.onDataReturnInitializeTable,
				//success : this.onDataReturnSetRows,
				failure : this.onDataReturnSetRows,
				argument : oState, // Pass along the new state to the callback
				scope : this
		};
		this._oDataSource.sendRequest(oReq, callback);
	 }; 
	 
    
	 ADT.prototype.update = function(interval, oReq){
		 this.updateStop();
		 if(ADT.isValidUpdateInterval(interval)){
			 this._updating = Lang.later(interval,this,function(_oReq){
				 log("AUTO_UPDATE REQUEST for instance " + this.instanceId);
				 this.refresh(_oReq);
			 },oReq,this.instanceId);
			 log("AUTO_UPDATE STARTED for instance " + this.instanceId);
		 }
	 }
	 
	 ADT.prototype.updateStop = function(interval, oReq){
		 if(this.isUpdate()){
			 log("AUTO_UPDATE STOPING for instance " + this.instanceId + ", updating dispatcher Id: " + this._updating.interval);
			 this._updating.cancel();
			 this._updating = false;
			 log("AUTO_UPDATE STOPED for instance " + this.instanceId);
		 }
	 }
	 
	 ADT.isValidUpdateInterval = function(interval){
		 return interval > 0;
	 }
	 
	 ADT.prototype.isUpdate = function(){
		 if(this._updating == null)
			 this._updating = false;
		 return !(this._updating === false);
	 }
	 
	 ADT.instancesCounter = 0;
	 
	 ADT.getInstacesCounter = function(){
		 return ADT.instanceCounter;
	 }
	 
    var log = function(message){
    	YAHOO.log(message, "info", "AnoDataTable");
    }
})();
YAHOO.register('anoweb.widget.AnoDataTable', YAHOO.anoweb.widget.AnoDataTable, {version: "0.99", build: '11'});

