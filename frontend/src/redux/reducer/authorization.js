const initialState = {
    name:null,
    password:null,

}

export function authorization(state = initialState, action) {

    switch (action.type) {
        case "ACTIONS_AUTHORIZATION_ENTRY":
            return {
                name:action.payload.name,
                password:action.payload.password
            }
        case "ACTIONS_AUTHORIZATION_CLOSE":
            return {
                name:null,
                password:null
            }

    }
    return state
}

