window.onload=function () {

    englishText = document.getElementById('english')
    russianText = document.getElementById('russian')
    ukrainianText = document.getElementById('ukrainian')

    function reloadTextArea() {
        englishText.innerText = ''
        russianText.innerText = ''
        ukrainianText.innerText = ''
    }

    document.getElementById('send').addEventListener('click', function () {
        if (confirm('Are you sure you want to send this message to ALL users? Make sure there are no typos in the text.')) {
            fetch('/custom-messages', {
                method: 'POST',
                credentials: "include",
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    englishText: englishText.value,
                    russianText: russianText.value,
                    ukrainianText: ukrainianText.value
                })
            }).catch(error => {
                console.log(error)
            })
            reloadTextArea()
        }
    })
}