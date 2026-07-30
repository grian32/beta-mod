const menuButton = document.querySelector("[data-menu-toggle]");
const menuScrim = document.querySelector("[data-sidebar-scrim]");
const themeButton = document.querySelector("[data-theme-toggle]");

function setNavigation(open) {
  document.body.classList.toggle("nav-open", open);
  menuButton?.setAttribute("aria-expanded", String(open));
}

menuButton?.addEventListener("click", () => {
  setNavigation(!document.body.classList.contains("nav-open"));
});

menuScrim?.addEventListener("click", () => setNavigation(false));

themeButton?.addEventListener("click", () => {
  const current = document.documentElement.dataset.theme;
  const next = current === "light" ? "dark" : "light";
  document.documentElement.dataset.theme = next;
  localStorage.setItem("pbe-wiki-theme", next);
});

