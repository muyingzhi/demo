
/**
 * Created by muyz on 2017/11/8.
 */

(function(window){
    $(document).ajaxStart(function() {
        if($("#winLoading")[0]==undefined) {
            $("<div id='winLoading' class='winLoading'></div>").appendTo("body");
            window.winLoading = $("#winLoading");
        }
        //-----
        window.winLoading.css("display","block");
        window.winLoading.focus();

    });
    $(document).ajaxError(function(event, jqxhr, settings, thrownError) {
        /** **/
        var resp = jqxhr.responseText;
        if(jqxhr.status===0){
            alert("异常，服务器未返回内容");
            return;
        }


        if(jqxhr.status===200){
            alert("异常，请求重定向，返回内容如下:status="+jqxhr.status+"\n"+resp);
            return;
        } else if(jqxhr.status == 401){
            alert("请登录后再访问")
            window.location.href = "/";
            return;
        }else if(jqxhr.status==403){
            alert("403，您的权限禁止此访问");
            return;
        }else if(jqxhr.status==404){
            alert("404，请求无效地址:status="+jqxhr.status+"\n"+settings.url);
            return;
        } else if(jqxhr.status==500){
            alert("500，服务端异常:status="+jqxhr.status+"\n"+settings.url);
            return;
        } else {
            alert("异常："+resp);
        }
        try{
            var result = $.parseJSON(resp);
        }catch(er){
            if(er.name === "SyntaxError"){
                alert("异常内容不是JSON：\n"+resp);
            }else{
                alert("异常内容：\n"+resp);
            }
            return;
        }
    });
    $(document).ajaxComplete(function(event, xhr, settings) {
        if(window.winLoading) {
            window.winLoading.css("display","none");
        }
    });
    $(document).ajaxStop(function() {

    });
}(window))
