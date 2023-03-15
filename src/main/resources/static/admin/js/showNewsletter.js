const sendNewsletterLink = document.querySelector(".send-newsletter");

sendNewsletterLink.addEventListener("click", (event) => {
    event.preventDefault();

    formContainer.innerHTML = `
    <div class="container text-center" style="margin-top: 50px; width: 100%"></div>
      <form class="newsletter-form form-signin text-center" th:action="@{/admin/sendMail}" method="POST">
        <h2>Wyslij newsletter</h2>
        <label for="title">Tytuł maila:</label>
        <input type="text" id="title" name="title" class="form-control" placeholder="Tytuł maila" required>
        
        <label for="subject">Treść maila:</label>
        <input type="text" id="subject" name="subject" class="form-control" placeholder="Treść maila" required>   
     
        <label for="inHtml">Treść w HTML?</label>
        <input type="checkbox" id="title" name="inHtml" class="form-control" required>
     
        <button type="submit">Wyslij</button>
      </form>
    `;
});
