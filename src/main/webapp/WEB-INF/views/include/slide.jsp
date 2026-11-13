<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<%-- 이미지 폴더 경로: 여기 한 줄만 고치면 3장 모두 바뀜 --%>
<c:set var="imgPath" value="${ctp}/images"/>
<style>
	.hero {
	  position: relative;
	  width: 100%;
	  height: 95vh;
	  overflow: hidden;
	  margin: 0;
	  background: #222;
	}
	.heroSlide {
	  position: absolute;
	  top: 0;
	  left: 0;
	  width: 100%;
	  height: 100%;              
	  opacity: 0;
	  transition: opacity 1s ease;
	}
	.heroSlide.active {
	  opacity: 1;
	}
	.heroSlide img {
	  width: 100%;
	  height: 100%;
	  object-fit: cover;
	  object-position: center;
	  filter: brightness(0.6);
	  display: block;
	}
	.hero-text {
	  position: absolute;
	  top: 50%;
	  left: 50%;
	  transform: translate(-50%, -50%);
	  width: 100%;
	  text-align: center;
	  color: white;
	  text-shadow: 2px 2px 8px #222;
	  z-index: 2;
	  pointer-events: none;
	}
	.hero-text h1 {
	  font-size: 44px;
	  font-weight: 400;
	  letter-spacing: 2px;
	  margin-bottom: 12px;
	}
	.hero-text p {
	  font-size: 16px;
	  margin: 0;
	  display: flex;
	  align-items: center;
	  justify-content: center;
	  gap: 14px;
	}
	.hero-text p::before,
	.hero-text p::after {
	  content: "";
	  width: 90px;
	  height: 1px;
	  background: rgba(255, 255, 255, 0.8);
	}
	.hero-dots {
	  position: absolute;
	  bottom: 16px;
	  left: 50%;
	  transform: translateX(-50%);
	  display: flex;
	  gap: 10px;
	  z-index: 3;
	}
	.hero-dots span {
	  width: 10px;
	  height: 10px;
	  border-radius: 50%;
	  border: 1px solid #fff;
	  cursor: pointer;
	}
	.hero-dots span.active { background: #fff; }
		@media (max-width: 768px) {
		  .hero { height: 260px; }
		  .hero-text h1 { font-size: 28px; }
		  .hero-text p::before, .hero-text p::after { width: 40px; }
		}
</style>

<div class="hero">
  <div class="heroSlide active"><img src="${imgPath}/16.jpg" alt="slide1"></div>
  <div class="heroSlide"><img src="${imgPath}/17.jpg" alt="slide2"></div>
  <div class="heroSlide"><img src="${imgPath}/18.jpg" alt="slide3"></div>

  <div class="hero-text">
    <h1>A Place to Call Home</h1>
    <p>We love having you here.</p>
  </div>

  <div class="hero-dots">
    <span class="active"></span>
    <span></span>
    <span></span>
  </div>
</div>

<script>
	(function() {
	  var slides = document.querySelectorAll(".hero .heroSlide");
	  var dots = document.querySelectorAll(".hero-dots span");   /* 점 목록으로 복구 */
	  var idx = 0;
	  var timer;

	  function show(n) {
	    idx = n;
	    for (var i = 0; i < slides.length; i++) {
	      slides[i].classList.toggle("active", i === idx);
	      dots[i].classList.toggle("active", i === idx);
	    }
	  }
	  function next() { show((idx + 1) % slides.length); }
	  function start() { timer = setInterval(next, 4000); }

	  dots.forEach(function(dot, i) {
	    dot.addEventListener("click", function() {
	      clearInterval(timer);
	      show(i);
	      start();
	    });
	  });

	  /* 이미지 로드 실패 시 콘솔에 정확한 경로를 알려줌 */
	  document.querySelectorAll(".heroSlide img").forEach(function(img) {
	    img.addEventListener("error", function() {
	      console.error("이미지 로드 실패:", img.src);
	    });
	  });

	  start();
	})();
</script>