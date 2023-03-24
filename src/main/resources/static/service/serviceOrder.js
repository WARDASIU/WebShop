const woodTypeSelect = document.createElement('select');
woodTypeSelect.classList.add('form-control');
woodTypeSelect.name = 'rodzaj-drzewa';
woodTypeSelect.required = true;

const oakOption = document.createElement('option');
oakOption.value = 'Dąb';
oakOption.text = 'Dąb';
woodTypeSelect.appendChild(oakOption);

const ashOption = document.createElement('option');
ashOption.value = 'jesion';
ashOption.text = 'Jesion';
woodTypeSelect.appendChild(ashOption);

const beechOption = document.createElement('option');
beechOption.value = 'buk';
beechOption.text = 'Buk';
woodTypeSelect.appendChild(beechOption);

const sosnaOption = document.createElement("option");
sosnaOption.value = "sosna";
sosnaOption.text = "Sosna";
woodTypeSelect.appendChild(sosnaOption);

function showAdditionalFields() {
    const category = document.getElementById("category").value;
    const additionalFieldsDiv = document.getElementById("additionalFields");
    additionalFieldsDiv.innerHTML = "";

    if (category === "schody") {
        const constructionSelect = document.createElement("select");
        constructionSelect.id = "constructionSelect";
        constructionSelect.classList.add("form-control");
        constructionSelect.name = "konstrukcja_schodow";
        constructionSelect.required = true;

        const emptyOption = document.createElement("option");
        emptyOption.value = "";
        emptyOption.text = "Wybierz konstrukcję";
        constructionSelect.appendChild(emptyOption);

        const woodenOption = document.createElement("option");
        woodenOption.value = "drewniana";
        woodenOption.text = "Konstrukcja drewniana";
        constructionSelect.appendChild(woodenOption);

        const metalOption = document.createElement("option");
        metalOption.value = "metalowa";
        metalOption.text = "Konstrukcja metalowa";
        constructionSelect.appendChild(metalOption);

        const constructionLabel = document.createElement("label");
        constructionLabel.setAttribute('for', 'rodzaj_konstrukcji');
        constructionLabel.innerText = "Jaka konstrukcja?";

        additionalFieldsDiv.appendChild(constructionLabel);
        additionalFieldsDiv.appendChild(constructionSelect);

        const stairsTypeDiv = document.createElement("div");
        constructionSelect.addEventListener("change", function () {
            stairsTypeDiv.innerHTML = "";
            if (constructionSelect.value === "drewniana") {
                additionalFieldsDiv.innerHTML = "";
                additionalFieldsDiv.appendChild(constructionLabel);
                additionalFieldsDiv.appendChild(constructionSelect);

                const stepsTypeSelect = document.createElement("select");
                stepsTypeSelect.id = "stepsType";
                stepsTypeSelect.classList.add("form-control");
                stepsTypeSelect.name = "typ_stopni_schodow";
                stepsTypeSelect.required = true;

                const regularOption = document.createElement("option");
                regularOption.value = "zwykle";
                regularOption.text = "Stopnie zwykłe";
                stepsTypeSelect.appendChild(regularOption);

                const carpetOption = document.createElement("option");
                carpetOption.value = "dywanowe";
                carpetOption.text = "Stopnie dywanowe";
                stepsTypeSelect.appendChild(carpetOption);

                const stairsFloatGround = document.createElement("select");
                stairsFloatGround.id = "stepsTypeFloatGround";
                stairsFloatGround.classList.add("form-control");
                stairsFloatGround.name = "typ_schodow";
                stairsFloatGround.required = true;

                const stairsFloatOption = document.createElement("option");
                stairsFloatOption.value = "wiszaca";
                stairsFloatOption.text = "Konstrukcja wisząca";
                stairsFloatGround.appendChild(stairsFloatOption);

                const stairsGroundOption = document.createElement("option");
                stairsGroundOption.value = "beton";
                stairsGroundOption.text = "Konstrukcja na betonie";
                stairsFloatGround.appendChild(stairsGroundOption);

                const stepsCountInput = document.createElement("input");
                stepsCountInput.id = "stepsCount";
                stepsCountInput.classList.add("form-control");
                stepsCountInput.type = "number";
                stepsCountInput.name = "ilosc_stopni_schodow";
                stepsCountInput.placeholder = "Ilość stopni";

                const woodenConstructionLabel = document.createElement("label");
                woodenConstructionLabel.setAttribute('for', 'rodzaj_konstrukcji');
                woodenConstructionLabel.innerText = "Parametry konstrukcji drewnianej:";

                const woodKindLabelSteps = document.createElement("label");
                woodKindLabelSteps.innerText = "Rodzaj drzewa - stopnie:";

                const woodKindLabelConstruction = document.createElement("label");
                woodKindLabelConstruction.innerText = "Rodzaj drzewa - konstrukcja:";

                const stairsWoodTypeSelect = document.createElement('select');
                stairsWoodTypeSelect.classList.add('form-control');
                stairsWoodTypeSelect.name = 'rodzaj_drzewa';
                stairsWoodTypeSelect.required = true;

                const oakOption = document.createElement('option');
                oakOption.value = 'dąb';
                oakOption.text = 'Dąb';
                stairsWoodTypeSelect.appendChild(oakOption);

                const ashOption = document.createElement('option');
                ashOption.value = 'jesion';
                ashOption.text = 'Jesion';
                stairsWoodTypeSelect.appendChild(ashOption);

                const beechOption = document.createElement('option');
                beechOption.value = 'buk';
                beechOption.text = 'Buk';
                stairsWoodTypeSelect.appendChild(beechOption);

                const sosnaOption = document.createElement("option");
                sosnaOption.value = "sosna";
                sosnaOption.text = "Sosna";
                stairsWoodTypeSelect.appendChild(sosnaOption);

                stairsTypeDiv.appendChild(woodenConstructionLabel);
                stairsTypeDiv.appendChild(stairsFloatGround);
                stairsTypeDiv.appendChild(stepsTypeSelect);
                stairsTypeDiv.appendChild(woodKindLabelSteps);
                stairsTypeDiv.appendChild(woodTypeSelect);
                stairsTypeDiv.appendChild(stepsCountInput);
                stairsTypeDiv.appendChild(woodKindLabelConstruction);
                stairsTypeDiv.appendChild(stairsWoodTypeSelect);

                additionalFieldsDiv.appendChild(stairsTypeDiv);
            } else if (constructionSelect.value === "metalowa") {
                additionalFieldsDiv.innerHTML = "";
                additionalFieldsDiv.appendChild(constructionLabel);
                additionalFieldsDiv.appendChild(constructionSelect);

                const colorInput = document.createElement("input");
                colorInput.classList.add("form-control");
                colorInput.type = "text";
                colorInput.name = "kolor_konstrukcji";
                colorInput.placeholder = "Kolor konstrukcji";

                const metalTypeSelect = document.createElement("select");
                metalTypeSelect.classList.add("form-control");
                metalTypeSelect.name = "rodzaj_konstrukcji";
                metalTypeSelect.required = true;

                const steelOption = document.createElement("option");
                steelOption.value = "stal_ocynkowana";
                steelOption.text = "Stal ocynkowana";
                metalTypeSelect.appendChild(steelOption);

                const aluminumOption = document.createElement("option");
                aluminumOption.value = "stal_nierdzewna";
                aluminumOption.text = "Stal nierdzewna";
                metalTypeSelect.appendChild(aluminumOption);

                const degreeInput = document.createElement("input");
                degreeInput.classList.add("form-control");
                degreeInput.type = "number";
                degreeInput.min = "0";
                degreeInput.max = "360";
                degreeInput.step = "1";
                degreeInput.name = "stopnie";
                degreeInput.placeholder = "Stopnie";

                const metalConstructionDiv = document.createElement("div");
                metalConstructionDiv.classList.add("form-group");

                const metalConstructionLabel = document.createElement("label");
                metalConstructionLabel.setAttribute('for', 'rodzaj_konstrukcji');
                metalConstructionLabel.innerText = "Parametry konstrukcji metalowej:";

                metalConstructionDiv.appendChild(metalConstructionLabel);
                metalConstructionDiv.appendChild(colorInput);
                metalConstructionDiv.appendChild(metalTypeSelect);
                metalConstructionDiv.appendChild(degreeInput);

                additionalFieldsDiv.appendChild(metalConstructionDiv);
            }
        });
    }//schody

    if (category === "altanka") {
        const woodKindInput = document.createElement("input");
        woodKindInput.classList.add("form-control");
        woodKindInput.type = "text";
        woodKindInput.name = "rodzaj_drzewa";
        woodKindInput.placeholder = "Rodzaj drzewa";

        const anglesInput = document.createElement("input");
        anglesInput.classList.add("form-control");
        anglesInput.type = "number";
        anglesInput.min = "3";
        anglesInput.name = "ilosc_katow";
        anglesInput.placeholder = "Ilość kątów";

        const altankaHeightInput = document.createElement("input");
        altankaHeightInput.classList.add("form-control");
        altankaHeightInput.type = "number";
        altankaHeightInput.name = "wysokosc";
        altankaHeightInput.placeholder = "Wysokość";

        const distanceBetweenAnglesAltanka = document.createElement("input");
        distanceBetweenAnglesAltanka.classList.add("form-control");
        distanceBetweenAnglesAltanka.type = "number";
        distanceBetweenAnglesAltanka.name = "odleglosc_jednego_boku";
        distanceBetweenAnglesAltanka.placeholder = "Odległość jednego boku";

        const woodenConstructionLabel = document.createElement("label");
        woodenConstructionLabel.setAttribute('for', 'rodzaj_konstrukcji');
        woodenConstructionLabel.innerText = "Parametry altanki:";

        additionalFieldsDiv.appendChild(woodenConstructionLabel);
        additionalFieldsDiv.appendChild(woodKindInput);
        additionalFieldsDiv.appendChild(anglesInput);
        additionalFieldsDiv.appendChild(altankaHeightInput);
        additionalFieldsDiv.appendChild(distanceBetweenAnglesAltanka);
    }

    if (category === "elementy_wewnatrz") {
        const elementType = document.createElement("select");
        elementType.classList.add("form-control");
        elementType.name = "typ_elementu";
        elementType.required = true;

        const optionParapet = document.createElement("option");
        optionParapet.value = "parapet";
        optionParapet.text = "Parapet";
        elementType.appendChild(optionParapet);

        const optionLamelka = document.createElement("option");
        optionLamelka.value = "lamelka";
        optionLamelka.text = "Lamelka";
        elementType.appendChild(optionLamelka);

        const optionPodloga = document.createElement("option");
        optionPodloga.value = "podloga";
        optionPodloga.text = "Podłoga";
        elementType.appendChild(optionPodloga);

        const optionOptoczenieKominka = document.createElement("option");
        optionOptoczenieKominka.value = "otoczenie_kominka";
        optionOptoczenieKominka.text = "Otoczenie kominka";
        elementType.appendChild(optionOptoczenieKominka);

        const elementTypeLabel = document.createElement("label");
        elementTypeLabel.innerText = "Jaki element?";

        additionalFieldsDiv.appendChild(elementTypeLabel);
        additionalFieldsDiv.appendChild(elementType);

        const elementChoosenType = document.createElement("div");
        elementType.addEventListener("change", function () {
            elementChoosenType.innerHTML = "";
            if (elementType.value === "lamelka") {
                additionalFieldsDiv.innerHTML = "";
                additionalFieldsDiv.appendChild(elementTypeLabel);
                additionalFieldsDiv.appendChild(elementType);

                const heightInput = document.createElement("input");
                heightInput.classList.add("form-control");
                heightInput.type = "number";
                heightInput.min = "100";
                heightInput.max = "300";
                heightInput.name = "wysokosc";
                heightInput.placeholder = "Wysokość (cm)";

                const thicknessInput = document.createElement("input");
                thicknessInput.classList.add("form-control");
                thicknessInput.type = "number";
                thicknessInput.min = "5";
                thicknessInput.max = "15";
                thicknessInput.step = "0.1";
                thicknessInput.name = "grubosc";
                thicknessInput.placeholder = "Grubość (mm)";

                const colorInput = document.createElement("input");
                colorInput.classList.add("form-control");
                colorInput.type = "text";
                colorInput.name = "kolor";
                colorInput.placeholder = "Kolor";

                const certainElementTypeLabel = document.createElement("label");
                certainElementTypeLabel.innerText = "Parametry elementu:";

                elementChoosenType.appendChild(certainElementTypeLabel);
                elementChoosenType.appendChild(woodTypeSelect);
                elementChoosenType.appendChild(heightInput);
                elementChoosenType.appendChild(thicknessInput);
                elementChoosenType.appendChild(colorInput);

                additionalFieldsDiv.appendChild(elementChoosenType);
            } else if (elementType.value === "podloga") {
                additionalFieldsDiv.innerHTML = "";
                additionalFieldsDiv.appendChild(elementTypeLabel);
                additionalFieldsDiv.appendChild(elementType);

                const flooringTypeSelect = document.createElement("select");
                flooringTypeSelect.classList.add("form-control");
                flooringTypeSelect.name = "rodzaj_podlogi";
                flooringTypeSelect.required = true;

                const parquetOption = document.createElement("option");
                parquetOption.value = "klepka";
                parquetOption.text = "Klepka";
                flooringTypeSelect.appendChild(parquetOption);

                const boardOption = document.createElement("option");
                boardOption.value = "deska";
                boardOption.text = "Deska";
                flooringTypeSelect.appendChild(boardOption);

                const beamInput = document.createElement("input");
                beamInput.classList.add("form-control");
                beamInput.type = "number";
                beamInput.min = "15";
                beamInput.max = "30";
                beamInput.step = "0.1";
                beamInput.name = "wason";
                beamInput.placeholder = "Wąson (cm)";

                const colorInput = document.createElement("input");
                colorInput.classList.add("form-control");
                colorInput.type = "text";
                colorInput.name = "kolor";
                colorInput.placeholder = "Kolor";

                const certainElementTypeLabel = document.createElement("label");
                certainElementTypeLabel.innerText = "Parametry elementu:";

                elementChoosenType.appendChild(certainElementTypeLabel);
                elementChoosenType.appendChild(flooringTypeSelect);
                elementChoosenType.appendChild(woodTypeSelect);
                elementChoosenType.appendChild(beamInput);
                elementChoosenType.appendChild(colorInput);

                additionalFieldsDiv.appendChild(elementChoosenType);
            } else if (elementType.value === "parapet") {
                additionalFieldsDiv.innerHTML = "";
                additionalFieldsDiv.appendChild(elementTypeLabel);
                additionalFieldsDiv.appendChild(elementType);

                const widthInput = document.createElement("input");
                widthInput.classList.add("form-control");
                widthInput.type = "number";
                widthInput.min = "50";
                widthInput.max = "300";
                widthInput.step = "1";
                widthInput.name = "szerokosc";
                widthInput.placeholder = "Szerokość (cm)";

                const heightInput = document.createElement("input");
                heightInput.classList.add("form-control");
                heightInput.type = "number";
                heightInput.min = "10";
                heightInput.max = "100";
                heightInput.step = "0.1";
                heightInput.name = "wysokosc";
                heightInput.placeholder = "Wysokość (cm)";

                const colorInput = document.createElement("input");
                colorInput.classList.add("form-control");
                colorInput.type = "text";
                colorInput.name = "kolor";
                colorInput.placeholder = "Kolor";

                const certainElementTypeLabel = document.createElement("label");
                certainElementTypeLabel.innerText = "Parametry elementu:";

                elementChoosenType.appendChild(certainElementTypeLabel);
                elementChoosenType.appendChild(widthInput);
                elementChoosenType.appendChild(heightInput);
                elementChoosenType.appendChild(woodTypeSelect);
                elementChoosenType.appendChild(colorInput);

                additionalFieldsDiv.appendChild(elementChoosenType);
            } else if(elementType.value === "optoczenie_kominka"){
                additionalFieldsDiv.innerHTML = "";
                additionalFieldsDiv.appendChild(elementTypeLabel);
                additionalFieldsDiv.appendChild(elementType);

                const widthInput = document.createElement('input');
                widthInput.classList.add('form-control');
                widthInput.type = 'number';
                widthInput.min = '50';
                widthInput.max = '300';
                widthInput.step = '1';
                widthInput.name = 'width';
                widthInput.placeholder = 'Szerokość (cm)';

                const heightInput = document.createElement('input');
                heightInput.classList.add('form-control');
                heightInput.type = 'number';
                heightInput.min = '50';
                heightInput.max = '300';
                heightInput.step = '1';
                heightInput.name = 'height';
                heightInput.placeholder = 'Wysokość (cm)';

                const colorInput = document.createElement('input');
                colorInput.classList.add('form-control');
                colorInput.type = 'text';
                colorInput.name = 'color';
                colorInput.placeholder = 'Kolor';

                const certainElementTypeLabel = document.createElement("label");
                certainElementTypeLabel.innerText = "Parametry elementu:";

                elementChoosenType.appendChild(certainElementTypeLabel);
                elementChoosenType.appendChild(widthInput);
                elementChoosenType.appendChild(heightInput);
                elementChoosenType.appendChild(woodTypeSelect);
                elementChoosenType.appendChild(colorInput);

                additionalFieldsDiv.appendChild(elementChoosenType);
            }
        });
    }
}