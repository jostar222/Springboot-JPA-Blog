let index = {
    init:function () {
        $("#btn-save").on("click", ()=>{ //function(){}, ()=>{} this를 바인딩하기 위해서 화살표함수를 사용!!
            this.save();
        });
    },

    save:function () {
        //alert("user의 save함수 호출됨");
        let data = {
            username:$("#username").val(),
            password:$("#password").val(),
            email:$("#email").val()
        };

        //console.log(data);

        //ajax호출 시 default가 비동기 호출
        //ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청!!
        //dataType을 아무것도 지정하지 않으면 jQuery는 응답의 MIME 유형을 기반으로 해석을 시도
        $.ajax({
            type:"POST",
            url: "/blog/api/user",
            data:JSON.stringify(data), //http body데이터
            contentType:"application/json;charset=utf-8", //body데이터가 어떤 타입인지(MIME)
            dataType:"json" //요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열(생긴게 json이라면) => javascript오브젝트로 변환
        }).done(function(resp){
            //요청 성공
            alert("회원가입이 완료되었습니다.");
            console.log(resp);
            //location.href="/blog";
        }).fail(function(error){
            //요청 실패
            alert(JSON.stringify(error));
        });

    }
}

index.init();