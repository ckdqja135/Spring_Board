<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>게시판 페이지</title>
    <!-- css 가져오기 -->
    <link rel="stylesheet" type="text/css" href="/coco/resources/semantic.min.css">
	<!-- css 가져오기 -->
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
            max-width: 1000px;
        }

        .view_btn {
            cursor: pointer;
        }

    </style>
</head>

<body>
    <div class="ui middle aligned center aligned grid">
        <div class="column">
            <h2 class="ui teal image header">
                게시판 페이지
            </h2>
            <div class="ui large form">
                <div class="ui stacked segment">
                    <a href="/coco/write_bbs"><button class="ui fluid large teal submit button">게시글 작성하기</button></a>
                    <table class="ui celled table">
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>제목</th>
                                <th>등록자</th>
                                <th>등록일</th>
                            </tr>
                        </thead>
                        <tbody id="list">
                        </tbody>
                    </table>
                </div>

                <div class="ui error message"></div>

            </div>
        </div>
    </div>

    <div class="ui modal" id='view_modal'>
        <i class="close">x</i>
        <div class="header" id="b_title">
            
        </div>
        <div class="content">
            <div class="description">
            	<p style = "text-align: right" id = "b_review"></p>
            	<div id = 'b_content'></div>
            </div>
        </div>
        <div class="actions">
            <div class="ui black deny button">
                닫기
            </div>
        </div>
    </div>

    <!-- js 가져오기 -->
    <script src="/coco/resources/jquery3.3.1.min.js"></script>
    <script src="/coco/resources/semantic.min.js"></script>

    <script>
        $(document).ready(function() {
            $.ajax({
                type: "get",
                url: "bbs_all",
                success: function(data) {
                	// 데이터 만큼 for문을 돌림
                    console.log(data);
                    for (var str in data) {
                    	// 변수 tr에 속성을 data-id로 data[str]['b_no']를 넣어주고 id가 list인 태그에 추가
                        var tr = $("<tr></tr>").attr("data-id", data[str]['b_no']).appendTo("#list");
                    	//tr에 td테이블 추가 및 클래스 추가 + 텍스트 수정
                        $("<td></td>").text(data[str]['b_no']).addClass("view_btn").appendTo(tr);
                        $("<td></td>").text(data[str]['b_title']).addClass("view_btn").appendTo(tr);
                        $("<td></td>").text(data[str]['b_ownernick']).addClass("view_btn").appendTo(tr);
                        $("<td></td>").text(FormatToUnixtime(data[str]['b_regdate'])).addClass("view_btn").appendTo(tr);
                    }
                },
                error: function(error) {
                    alert("오류 발생" + error);
                }
            });

            $(document).on("click", ".view_btn", function() {
            	// 해당하는 태크 속성중 DATA-ID를 가져와서 B_NO에 담음
                var b_no = $(this).parent().attr("data-id");

                $.ajax({
                    type: "get",
                    url: "get_bbs",
                    data: {
                        b_no: b_no
                    },
                    success: function(data) {
                    	// 해당하는 ID에 텍스트문 변경
                    	console.log(data);
                    	$("#b_title").text(data['b_title']);
                    	$("#b_review").text(data['b_ownernick'] + " - " +  FormatToUnixtime(data['b_regdate']));
                    	$("#b_content").text(data['b_content']);
                    	// modal을 실행
                    	$('#view_modal').modal('show');
                    },
                    error: function(error) {
                        alert("오류 발생" + error);
                    }
                });
            });

            function FormatToUnixtime(unixtime) {
                var u = new Date(unixtime);
                return u.getUTCFullYear() +
                    '-' + ('0' + u.getUTCMonth()).slice(-2) +
                    '-' + ('0' + u.getUTCDate()).slice(-2) +
                    ' ' + ('0' + u.getUTCHours()).slice(-2) +
                    ':' + ('0' + u.getUTCMinutes()).slice(-2) +
                    ':' + ('0' + u.getUTCSeconds()).slice(-2)
            };
        });

    </script>
</body>

</html>
