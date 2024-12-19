document.addEventListener("DOMContentLoaded", () => {
    const slides = document.querySelectorAll(".carousel-slide");
    let currentSlide = 0;

    function showSlide(index) {
        slides.forEach((slide, i) => {
            slide.style.display = i === index ? "flex" : "none";
        });
    }

    function nextSlide() {
        currentSlide = (currentSlide + 1) % slides.length;
        showSlide(currentSlide);
    }

    // Initial setup to show the first slide
    showSlide(currentSlide);

    // Set interval to automatically change slides every 5 seconds
    setInterval(nextSlide, 5000);
});
