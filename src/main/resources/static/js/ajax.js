
/**
 * Created by muyz on 2017/11/8.
 */

(function(window){
    var xmlhttp;
    var xmlcallback = null;
    var authToken = "";
    window.ajax = function(url,datas,callback){
        var obj = new Ajax(url,datas,callback);
        obj.loadXMLDoc();
    }
    var Ajax = function(url,datas,callback){
        this.url = url;
        this.datas = datas;
        xmlcallback = callback;
    }
    Ajax.prototype.loadXMLDoc = function()
    {
        var sdatas = JSON.stringify(this.datas);

        xmlhttp=null;
        if (window.XMLHttpRequest)
        {// code for all new browsers
            xmlhttp=new XMLHttpRequest();
        }
        else if (window.ActiveXObject)
        {// code for IE5 and IE6
            xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
        }
        if (xmlhttp!=null)
        {
            xmlhttp.set
            xmlhttp.onreadystatechange=state_Change;
//            xmlhttp.setRequestHeader("accept","appliction/jso");
            xmlhttp.open("POST",this.url,true);
            xmlhttp.setRequestHeader("content-type","application/json")
            var authToken = localStorage.getItem("token");
            xmlhttp.setRequestHeader("X-AUTH-TOKEN",authToken);
            xmlhttp.send(sdatas);
        }
        else
        {
            alert("Your browser does not support XMLHTTP.");
        }
    }

    function state_Change ()
    {
        if (xmlhttp.readyState==4)
        {// 4 = "loaded"
            var authToken = xmlhttp.getResponseHeader("X-AUTH-TOKEN");
            localStorage.setItem("token",authToken);
            if (xmlhttp.status==200)
            {// 200 = OK
                console.log(xmlhttp)
                xmlcallback();
            }
            else
            {
                alert("Problem retrieving XML data");
            }
        }
    }
}(window))
