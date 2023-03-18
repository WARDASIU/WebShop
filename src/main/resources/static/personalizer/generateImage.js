function generateAIImage(){
    const form = document.querySelector('#test');
    const waitingMessage = document.querySelector('#waiting-message');

    form.addEventListener('submit', async (event) => {
        event.preventDefault();

        waitingMessage.style.display = 'block';

        const formData = new FormData(event.target);
        console.log(`big color ${formData.get('tableColor')} table 
                 with color ${formData.get('resinColor')} resin/epoxy
                 ${formData.get('tableShape')} shape 
                 with ${formData.get('legType')} legs
        `);
        const response = await fetch('https://api.openai.com/v1/images/generations', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer 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'
            },
            body: JSON.stringify({
                model: 'image-alpha-001',
                prompt: `big ${formData.get('tableColor')} table 
                 with ${formData.get('resinColor')} resin/epoxy
                 ${formData.get('tableShape')} shape 
                 with ${formData.get('legType')} legs`,
                num_images: 1,
                size: '512x512',
                response_format: 'url'
            })
        });

        const data = await response.json();
        const image = new Image();
        image.src = data.data[0].url;

        await new Promise(resolve => {
            image.onload = resolve;
        });

        waitingMessage.style.display = 'none';

        const container = document.querySelector('#coffee-table-image-container');
        container.innerHTML = '';

        container.appendChild(image);
    });
}
