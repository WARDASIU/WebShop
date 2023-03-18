function updateDimensions() {
    var width = document.getElementById("width-slider").value;
    var height = document.getElementById("height-slider").value;
    var depth = document.getElementById("depth-slider").value;

    document.getElementById("width").value = width;
    document.getElementById("height").value = height;
    document.getElementById("depth").value = depth;
}

const resinColorInput = document.getElementById("resinColorInput");
const colorPicker = document.getElementById("colorPicker");

// colorPicker.addEventListener("input", () => {
//     resinColorInput.value = colorPicker.value;
// });

const legTypeSelect = document.getElementById("legType");
const metalLegsDiv = document.getElementById("metalLegs");
const woodLegsDiv = document.getElementById("woodType");

legTypeSelect.addEventListener("change", (event) => {
    if (event.target.value === "metal") {
        metalLegsDiv.style.display = "block";
        woodLegsDiv.style.display = "none";
    } else {
        metalLegsDiv.style.display = "none";
        woodLegsDiv.style.display = "block";
    }
});
