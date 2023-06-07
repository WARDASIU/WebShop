const API_KEY = "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"; //sal
const API_KEY2 = "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"; //pas

async function generateAIImage() {
    const form = document.querySelector('#personalizer-form');
    const spinner = document.querySelector('.spinner-border');

    spinner.classList.remove('d-none');

    const formData = new FormData(form);
    const dataToTranslate = [
        { 'rodzaj_nóg': formData.get('rodzaj_nóg') },
        { 'rodzaj_stołu': formData.get('rodzaj_stołu') },
        { 'kształt_stołu': formData.get('kształt_stołu') }
    ];

    const formDataToEnglish = await translate(dataToTranslate);

    console.log(`big ${formData.get('kolor_stołu')} colored ${formDataToEnglish.get('rodzaj_stołu')} table                
                 with ${formData.get('kolor_żywicy')} colored resin/epoxy in
                 and ${formDataToEnglish.get('kształt_stołu')} shape 
                 with ${formDataToEnglish.get('rodzaj_nóg')} legs
        `);

    const response = await fetch('https://api.openai.com/v1/images/generations', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${API_KEY}`
        },
        body: JSON.stringify({
            model: 'image-alpha-001',
            prompt: `big ${formData.get('kolor_stołu')} colored ${formData.get('rodzaj_stołu')} table with ${formData.get('kolor_żywicy')} colored resin/epoxy in and ${formData.get('kształt_stołu')} shape with ${formData.get('rodzaj_nóg')} legs`,
            num_images: 1,
            size: '512x512',
            response_format: 'url'
        })
    }).catch(error => {
        console.error('Error sending data:', error);
    }).finally(() => {
        spinner.classList.add('d-none');
    });

    const data = await response.json();
    const image = new Image();
    image.src = data.data[0].url;

    await new Promise(resolve => {
        image.onload = resolve;
    });

    const container = document.querySelector('#coffee-table-image-container');
    container.innerHTML = '';

    container.appendChild(image);

}

const translations = {
    rodzaj_stołu: {
        biurowy: 'office',
        jadalny: 'dinning',
        kawowy: 'coffee',
        konferencyjny: 'conference',
    },
    rodzaj_nóg: {
        drewno: 'wooden',
        metal: 'metal',
    },
    kształt_stołu: {
        prostokąt: 'rectangle',
        okrągły: 'round',
        owalny: 'oval',
        kwadrat: 'square',
    },
    kolor_stołu: {
        '#F2EDEB': 'beige',
        '#000000': 'black',
        '#FFFFFF': 'white',
        '#FF0000': 'red',
        '#00FF00': 'green',
        '#0000FF': 'blue',
    },
};

async function translate(data) {
    const formDataToEnglish = new FormData();

    data.map(obj => {
        const key = Object.keys(obj)[0];
        const value = obj[key];

        formDataToEnglish.append(key, translations[key]?.[value] ?? value);
    });

    return formDataToEnglish;
}