window.onload=function () {

    let englishText = document.getElementById('english')
    let russianText = document.getElementById('russian')
    let ukrainianText = document.getElementById('ukrainian')

    function reloadTextArea() {
        englishText.value = ''
        russianText.value = ''
        ukrainianText.value = ''
    }

    document.getElementById('send').addEventListener('click', function () {
        if (confirm('Are you sure you want to send this message to selected users? Make sure there are no typos in the text.')) {
            fetch('/custom-messages', {
                method: 'POST',
                credentials: "include",
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    englishText: englishText.value,
                    russianText: russianText.value,
                    ukrainianText: ukrainianText.value,
                    mode: document.querySelector('input[name="users-group"]:checked').value
                })
            }).catch(error => {
                console.error(error)
            })
            reloadTextArea()
        }
    })
}