import React from "react"
import Header from "./component/Header/Header";
import {connect} from "react-redux";
import {getList} from "./request/GET/getList";


class App extends React.Component {

    componentDidMount() {
        this.props.getList()
    }

    render() {
        return (
            <>
                <div className="App">
                    <Header/>
                    <div className="container">

                    </div>
                </div>
            </>
        )
    }
}
const mapStateToProps = ({authorization}) => {
    console.log(authorization)
    return {

    }
}
const mapStateToDispatch = dispatch => {
    return {

        getList: () => dispatch(getList()),
    };
};
export default connect(mapStateToProps,mapStateToDispatch)(App)
