document.addEventListener("DOMContentLoaded", () => {
  const switchButton = document.getElementById("switch");

  // Verificar la preferencia almacenada en el localStorage
  const darkMode = localStorage.getItem("darkMode") === "true";
  document.body.classList.toggle("dark-theme", darkMode);
  switchButton.checked = darkMode;

  // Al hacer clic en el interruptor, cambia el tema y guarda la preferencia
  switchButton.addEventListener("change", () => {
      const isDarkMode = switchButton.checked;
      document.body.classList.toggle("dark-theme", isDarkMode);
      localStorage.setItem("darkMode", isDarkMode);
  });
});

document.addEventListener("DOMContentLoaded", () => {
  const fileInput = document.getElementById("file");
  const previewContainer = document.createElement("div");
  previewContainer.id = "previewContainer"; // Mantener el mismo ID
  fileInput.insertAdjacentElement("afterend", previewContainer); // Insertar después del input

  fileInput.addEventListener("change", (event) => {
    previewContainer.innerHTML = ""; // Limpiar la previsualización anterior
    const files = event.target.files;

    Array.from(files).forEach((file) => {
      // Crear elemento para mostrar información del archivo
      const fileInfo = document.createElement("p");
      fileInfo.textContent = `Nombre: ${file.name}, Tipo: ${file.type || "Desconocido"}`;
      previewContainer.appendChild(fileInfo); // Agregar información al contenedor

      // Si es un archivo de imagen, crear una previsualización
      if (file.type.startsWith("image/")) {
        const imgPreview = document.createElement("img");
        imgPreview.classList.add("preview-image");
        imgPreview.src = URL.createObjectURL(file); // Crear URL del objeto
        imgPreview.onload = () => URL.revokeObjectURL(imgPreview.src); // Liberar memoria
        previewContainer.appendChild(imgPreview); // Agregar la imagen al contenedor
      }
    });
  });
});

