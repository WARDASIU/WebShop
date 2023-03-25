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



const form = document.getElementById('personalizer-form');
const alert = document.querySelector('.alert');
const spinner = document.querySelector('.spinner-border');

form.addEventListener('submit', (event) => {
    event.preventDefault();
    spinner.classList.remove('d-none');

    const formData = new FormData(event.target);
    const selectFields = document.querySelectorAll('select');
    const inputFields = document.querySelectorAll('input');

    selectFields.forEach(field => {
        if (field.value !== "") {
            formData.append(field.name, field.value);
        }
    });

    inputFields.forEach(field => {
        if (field.value !== "") {
            formData.append(field.name, field.value);
        }
    });

    console.log(formData.toString());
    console.log(formData);

    fetch('/personalizeOrder', {
        method: 'POST',
        body: formData
    })
        .then(response => {
            if (response.ok) {
                alert.classList.remove('d-none');
                alert.querySelector('strong').textContent = "Zapytanie o usługę zostało wysłane!";
                form.reset();
            } else {
                alert.classList.remove('d-none');
                alert.querySelector('strong').textContent = 'An error occurred while sending the email';
            }
        })
        .catch(error => {
            console.error('Error sending data:', error);
        }).finally(() => {
        spinner.classList.add('d-none');
    });
});