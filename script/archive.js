// Slideshow functionality
const slides = document.querySelectorAll('.slide');
let currentSlide = 0;
let slideInterval;

function showNextSlide() {
    slides[currentSlide].classList.remove('active');
    currentSlide = (currentSlide + 1) % slides.length;
    slides[currentSlide].classList.add('active');
}

function startSlideShow() {
    slideInterval = setInterval(showNextSlide, 3000); // Slide every 3 seconds
}

function stopSlideShow() {
    clearInterval(slideInterval);
}

slides.forEach(slide => {
    slide.addEventListener('mouseover', function() {
        stopSlideShow();
        const video = this.querySelector('video');
        if (video) {
            video.play();
            video.style.display = 'block'; // Ensure video is displayed
        }
    });

    slide.addEventListener('mouseout', function() {
        const video = this.querySelector('video');
        if (video) {
            video.pause();
            video.currentTime = 0;
            video.style.display = 'none'; // Hide video again
        }
        startSlideShow();
    });
});

// News 

const API_KEY = '2f2eb2727ef34d6e8af54ab40719f041';
const url = "https://newsapi.org/v2/everything?q=";
const currentDate = new Date();
const pastDate = new Date();
pastDate.setDate(currentDate.getDate() - 6); // Set past date to 6 days ago

window.addEventListener("load", () => fetchNews("India Sport"));

async function fetchNews(query) {
    try {
        const res = await fetch(`${url}${encodeURIComponent(query)}&apiKey=${API_KEY}`);
        const data = await res.json();

        if (data.status === "ok" && data.articles.length > 0) {
            // Filter articles to include only those within the last 6 days
            const recentArticles = data.articles.filter((article) => {
                const articleDate = new Date(article.publishedAt);
                return articleDate >= pastDate && articleDate <= currentDate;
            });

            if (recentArticles.length > 0) {
                bindData(recentArticles.slice(0, 6)); // Fetch up to 8 recent articles if available
            } else {
                console.log("No relevant sports news articles found in India within the last 6 days.");
            }
        } else {
            console.log("No relevant sports news articles found.");
        }
    } catch (error) {
        console.error("Network error:", error);
    }
}

function bindData(articles) {
    if (articles.length === 0) return; // Exit if no articles

    // Display the main news item
    const mainNews = articles[0];
    if (mainNews && mainNews.urlToImage) {
        document.getElementById('main-news-image').src = mainNews.urlToImage;
        document.getElementById('main-news-title').textContent = mainNews.title;
    }

    // Populate news list with remaining articles
    const newsList = document.getElementById("news-list");
    newsList.innerHTML = ""; // Clear any previous content

    articles.slice(1).forEach((article) => {
        if (!article.urlToImage) return;

        const newsItem = document.createElement("li");

        const dateSpan = document.createElement("span");
        dateSpan.textContent = new Date(article.publishedAt).toLocaleDateString();
        newsItem.appendChild(dateSpan);
        newsItem.appendChild(document.createElement("br"));

        const newsText = document.createTextNode(article.title);
        newsItem.appendChild(newsText);

        newsItem.addEventListener("click", () => {
            window.open(article.url, "_blank");
        });

        newsList.appendChild(newsItem);
    });
}