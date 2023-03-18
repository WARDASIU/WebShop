function generateAIImage(){
    const form = document.querySelector('#test');
    const waitingMessage = document.querySelector('#waiting-message');

    form.addEventListener('submit', async (event) => {
        event.preventDefault();

        waitingMessage.style.display = 'block';

        const formData = new FormData(event.target);
        console.log(`big ${formData.get('tableColor')} colored ${formData.get('tableType')} table                
                 with ${formData.get('resinColor')} colored resin/epoxy in
                 and ${formData.get('tableShape')} shape 
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
                prompt: `big ${formData.get('tableColor')} colored ${formData.get('tableType')} table with ${formData.get('resinColor')} color epoxy in and ${formData.get('tableShape')} shape with ${formData.get('legType')} legs`,
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
