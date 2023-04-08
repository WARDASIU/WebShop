window.onload = function () {
    const formContainer = document.querySelector('.order-container');

    fetch('/api/orders')
        .then(response => response.json())
        .then(orders => {
            const table = document.createElement('table');
            table.classList.add('table');
            table.innerHTML = `
            <thead>
                <tr>
                    <th>Adres</th>
                    <th>Imię</th>
                    <th>Nazwisko</th>
                    <th>Kod pocztowy</th>
                    <th>Numer telefonu</th>
                </tr>
            </thead>
            <tbody>
            </tbody>
        `;

            const tbody = table.querySelector('tbody');
            orders.forEach(item => {
                const tr = document.createElement('tr');
                const tdName = createEditableCell(item.name, 'name');
                const tdSurname = createEditableCell(item.name, 'surname');
                const tdAddress = createEditableCell(item.address, 'address');
                const tdPostCode = createEditableCell(item.postCode, 'postCode');
                const tdPhone = createEditableCell(item.phone, 'phone');

                const downloadButton = document.createElement('button');
                downloadButton.textContent = 'Pobierz fakture dla zamówienia';
                downloadButton.addEventListener('click', () => {
                    downloadFile(item.idOrder.toString());
                });

                tr.appendChild(tdName);
                tr.appendChild(tdSurname);
                tr.appendChild(tdAddress);
                tr.appendChild(tdPostCode);
                tr.appendChild(tdPhone);
                tr.appendChild(downloadButton);

                tbody.appendChild(tr);
            });

            formContainer.appendChild(table);
        });
}

function createEditableCell(value, field) {
    const td = document.createElement('td');
    td.textContent = value;
    td.dataset.field = field;
    td.dataset.editable = true;
    return td;
}

function downloadFile(orderId) {
    const url = 'downloadInvoice/' + orderId.toString();
    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.blob();
        })
        .then(blob => {
            const url = URL.createObjectURL(blob);
            const link = document.createElement('a');
            link.href = url;
            link.download = 'Faktura_nr' + orderId + '.pdf';
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
        });
}