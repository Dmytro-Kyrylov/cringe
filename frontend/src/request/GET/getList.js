

export function ActionJwtToken(payload) {

    return {
        type: "ACTIONS_GET_LIST",
        payload
    }
}

export function getList() {

    return (dispatch) => {
        let x = {
            Username:"Vadim",
            Password:"kirill"
        }
        try {
            const serverResponse = fetch(`https://f-cringe.herokuapp.com:443/api/list`, {
                method: 'GET',
                headers: {
                    'content-type': 'application/json',
                    'Authorization': 'Basic ' + btoa('Vadim:kirill')

                },

            }).then(async response => {

                return response.json()
            })
            return serverResponse
                .then(response => {
console.log(response)
                    return response
                })
                .catch(() => {

                });

        } catch (e) {

        }

    }
}