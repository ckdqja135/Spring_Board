<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>게시글 작성하기</title>
    <!-- css 가져오기 -->
    <link rel="stylesheet" type="text/css" href="/coco/resources/semantic.min.css">

    <style type="text/css">
        body {
            background-color: #DADADA;
        }

        body>.grid {
            height: 100%;
        }

        .image {
            margin-top: -100px;
        }

        .column {
            max-width: 450px;
        }

    </style>
</head>

<body>
    <div class="ui middle aligned center aligned grid">
        <div class="column">
            <h2 class="ui teal image header">
                게시글 작성하기
            </h2>
            <form class="ui large form">
                <div class="ui stacked segment">
                    <div class="field">
                        <input type="text" id="b_title" placeholder="게시글 제목" autocomplete="off" autofocus="autofocus">
                    </div>
                    <div class="field">
                        <div class="ui left icon input">
                            <textarea style="resize: vertical;" id="b_content" placeholder="게시글 내용" rows="8"></textarea>
                        </div>
                    </div>
                    <div class="ui fluid large teal submit button" id="write_bbs">게시글 작성하기</div>
                </div>

                <div class="ui error message"></div>

            </form>

            <a href="/coco/main"><button class="ui fluid large teal submit button">뒤로가기</button></a>
        </div>
    </div>
    <!-- js 가져오기 -->
    <script src="/coco/resources/jquery3.3.1.min.js"></script>
    <script src="/coco/resources/semantic.min.js"></script>
    <script>
        $(document).ready(function() {
        	// id가 write_bbs를 클릭할 때
            $("#write_bbs").click(function() {
            	// json 객체에 값을 담아줌.
                var json = {
                    b_title: $("#b_title").val(),
                    b_content: $("#b_content").val()
                };
            	
				// 변수명이 json에 담아둔 값만큼 for each문을 돌림.
                for (var str in json) {
                	// json의 str의 길이가 0인 경우
                    if (json[str].length == 0) {
                    	// 해당하는 id에 placeholder를 찾아 경고창을 띄운다.
                        alert($("#" + str).attr("placeholder") + "를 입력해주세요.");
                    	// 해당하는 id에 포커스를 올림
                        $("#" + str).focus();
                    	// 리턴
                        return;
                    }
                }
				// 비동기 요청
                $.ajax({
                    type: "post", // post로
                    url: "write_bbs", // url 지정
                    data: json, // 전달값은 json
                    success: function(data) { // 성공시
                        switch (Number(data)) {
                        // 받아온 데이터를 스위치문 사용
                        // -1은 로그인 안될 때, 0은 성공, 나머지는 db오류
                            case -1:
								alert("로그인 후 이용해주세요.");
								window.location.href = "/coco";
                                break;
                            case 0:
								alert("정상적으로 등록이 되었습니다.");
								window.location.href = "/coco/main";
                                break;

                            default:
                            	alert("알수없는 오류가 발생 했습니다.[Error Code : " + Number(data) + "]");
                                break;
                        }
                    },
                    error: function(error) {
                        alert("오류 발생" + error);
                    }
                });
            });
        });

    </script>
</body>

</html>
