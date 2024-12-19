// Function to open specific section
function openSection(sectionName) {
    const sections = document.getElementsByClassName("event-section");
    const tabs = document.getElementsByClassName("tab-link");

    // Hide all sections and remove active class from tabs
    for (let i = 0; i < sections.length; i++) {
        sections[i].style.display = "none";
    }
    for (let i = 0; i < tabs.length; i++) {
        tabs[i].classList.remove("active");
    }

    // Show the current section and set the clicked tab as active
    document.getElementById(sectionName).style.display = "block";
    event.currentTarget.classList.add("active");
}

// Set default tab to open on page load
document.getElementById("defaultOpen").click();
