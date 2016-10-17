Ext.define('model.SysUserModel', {
    extend: 'Ext.data.Model',  
    fields: [
    #replaceFeldArea
    ]
	,
	proxy: {
	    type: 'rest',
	    url:'/imes/sys/user'
	}
});