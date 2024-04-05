let htmlSourceObject = {
    id: `./html/formSearchById.html`,
    phone: `./html/formSearchByPhone.html`,
    email: `./html/formSearchByEmail.html`,
    lastName: `./html/formSearchByLastName.html`,
    fullName: `./html/formSearchByFullName.html`
}

const searchBy = (field) => {
    fetch(htmlSourceObject[field])
        .then(response => response.text())
        .then(htmlContent => {
            document.getElementById("Form").innerHTML = htmlContent;
        })
        .catch(error => {
            console.error(`error fetching html: `, error)
        })
}

function inputValidation(inputElement) {
    inputElement.classList.remove("is-invalid", "is-valid");

    let regex = '/^[A-Za-z]+([\ A-Za-z]+)*/';
    if (inputElement.value === "" || inputElement.value.test(regex)) {
        inputElement.classList.add("is-invalid");
        inputElement.nextElementSibling
            ?.classList.remove("d-none");
    } else {
        inputElement.classList.add("is-valid");
        inputElement.nextElementSibling
            ?.classList.add("d-none");
    }
}

function removeParamFromURL(param) {
    const urlObj = new URL(window.location.href);
    urlObj.searchParams.delete(param);
    window.location.href = urlObj.href;
}

function loading() {
    const modal = new bootstrap.Modal('#static_Backdrop');
    modal.show();
}