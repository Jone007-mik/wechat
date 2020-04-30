// JavaScript Document
$(function(){
	//本地地址
	var address="localhost";
	//正式地址
	//var address="http://f4qcgsh.hn3.mofasuidao.cn";

    /*var url=location.href.split("&")[0];
    var arr=url.split("=");
    if(arr.length>=2){
    	var code=arr[1];
    	alert(code);
    	console.log(code);
        $.ajax({
            type:"post",
            url:"/wechat/oauth",
            data:{"code":code},
            dataType:"json",
			async:false,
            success: function(data){
                //使用 localStorage 创建一个本地存储的 name/value 对，name="lastname" value="Smith", 然后检索 "lastname" 的值，并插入到 id="result" 的元素上:
                //存值
                alert(data.data);
                localStorage.setItem("data", data.data)
                //取值localStorage.getItem("data");
            }
        });
	}*/
    getUserInfo();
    function getUserInfo(){
        var openid=localStorage.getItem("openid");
        if(openid!=null && openid!=""){
            return;
        }
        alert(location.href);
        var url=location.href.split("&")[0];
        var arr=url.split("=");
        if(arr.length>=2){
            var code=arr[1];
            alert(code);
            console.log(code);
            $.ajax({
                debug:true,
                type:"post",
                url:"/wechat/oauth",
                data:{"code":code},
                dataType:"json",
                async:false,
                success: function(data){
                    //使用 localStorage 创建一个本地存储的 name/value 对，name="lastname" value="Smith", 然后检索 "lastname" 的值，并插入到 id="result" 的元素上:
                    //存值
                    //alert(data.data);
                    localStorage.setItem("openid", data.data)
                    //取值localStorage.getItem("data");
                }
            });
        }
    }


	//计算内容上下padding
	reContPadding({main:"#main",header:"#header",footer:"#footer"});
	function reContPadding(o){
		var main = o.main || "#main",
			header = o.header || null,
			footer = o.footer || null;
		var cont_pt = $(header).outerHeight(true),
			cont_pb = $(footer).outerHeight(true);
		$(main).css({paddingTop:cont_pt,paddingBottom:cont_pb});
	}
});


//折叠展开列表内容
$(document).ready(function(){
  mui('#slider').on('tap', '.open-btn', function (e) {
	  $(".nav-con").fadeToggle("fast");
	  $(".open-btn span").toggleClass('rotate'); 	
	  if($(".open-btn span").hasClass('rotate')){
		 $("#slider").on("touchmove.ddd",function(e){
			// console.log(e)
			  e.stopPropagation();
		});
	  }else{
		  console.log(1)
		 $("#slider").off("touchmove.ddd");  
	  }
  });
});