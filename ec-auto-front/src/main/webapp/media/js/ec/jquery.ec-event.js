// 创建一个闭包  
(function($) {
	//上传文件
	$.fn.uploadFile = function(options) {
		var settings = $.extend({
			auto          : true,                  //是否自动提交
			fileTypeDesc  : '请选择文件',
            fileTypeExts  : '*',
            uploader      : null,                  //提交的地址,
            onDialogClose : null,
            onDialogOpen  : null,
            onUploadSuccess : null,                 //上传成功后的回调
            onCancel  : null,                       //当点击文件队列中文件的关闭按钮或点击取消上传时触发            
            onSelect  :null,
            swf:null
            //选择上传文件后调用
		}, options || {}),
		$this=$(this);
		$this.uploadify(
		{
	    	 'buttonText'    : '选择文件',
	    	 'auto'          : settings.auto,
	    	 'height'        : 20,
	    	 'width'         : 80,
	    	 'removeTimeout' : 1,
	    	 'queueSizeLimit': 1,   //队列中允许的最大文件数目
	    	 'queueID'       : 'fileQueue',
	    	 'multi'         : false,
	    	 'fileTypeDesc'  : settings.fileTypeDesc,
             'fileTypeExts'  : settings.fileTypeExts, 
	    	 'swf'           : settings.swf,
		     'uploader'      : settings.uploader, 
		     'onDialogClose' : function(queueData) {
		    	   if (settings.onDialogClose) {
						settings.onDialogClose(queueData);
				   }
		     },
		     'onDialogOpen'  : function(queueData) {
		    	   if (settings.onDialogOpen) {
						settings.onDialogOpen(queueData);
				   }
		     },
             'onUploadSuccess' : function(file, data, response){
            	   if (settings.onUploadSuccess) {
                	    data=eval("("+data+")");
						settings.onUploadSuccess(data);
				   }
	         },
	         'onSelect' : function(file){
		      	   if (settings.onSelect) {
						settings.onSelect(file);
				   }
	         },
	         'onCancel' : function(event,ID,fileObj,data){
		      	   if (settings.onCancel) {
						settings.onCancel(event,ID,fileObj,data);
				   }
	         }
		 });
	};
	// 闭包结束
})(jQuery);
