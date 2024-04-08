import * as React from 'react';
import classNames from 'classnames';
import {request, setAuthHeader} from "../axios_helper";

export default class LoginForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
          active: "login",
          firstName: "",
          lastName: "",
          email: "",
          password: "",
        };
    }


    onLogin = (e, email, password) => {
        e.preventDefault();
        request("POST",
            "/api/v1/auth/authenticate", { email, password })
            .then((response) => {
                setAuthHeader(response.data.token);
                this.props.navigate("/feed");
            })
            .catch((error) => {
                setAuthHeader(null);
                if (error.response) {
                    //const errMessage = error.response.data;
                    const errCode = error.response.status;

                    if (errCode === 403) {
                        this.setState({ error: "Password is wrong. Please try again" });
                    }

                    if (errCode === 409) {
                        this.setState({ error: "User with the provided email was not found." });
                    }
                } else {
                    this.setState({ error: "Error connecting to the server. Please try again later." });
                }

            });
    };

    onSubmitLogin = (e) => {
        e.preventDefault();

        const { email, password } = this.state;

        if (!email.trim() || !password.trim()) {
            this.setState({ error: "Email and password are required." });
            return;
        }

        this.onLogin(e, email, password);
    };

    onRegister = (e, firstName, lastName, email, password) => {
        e.preventDefault();
        request(
            "POST",
            "/api/v1/auth/register", { firstName, lastName, email, password })
            .then(
                (response) => {
                    this.setState({ active: "confirmation" })
                    console.log("confirmation should be sent")
                })
            .catch((regError) => {
            setAuthHeader(null);
            if (regError.response) {
                this.setState({ error: "Something went wrong" });
                }
            });
    };

    onSubmitRegister = (e) => {
        e.preventDefault();

        const { firstName, lastName, email, password } = this.state;

        if (!firstName.trim() || !lastName.trim() || !email.trim() || !password.trim()) {
            this.setState({ error: "All fields are required." });
            return;
        }

        this.onRegister(e, firstName, lastName, email, password);
    };

    onSubmitForgotPassword = (e) => {
        e.preventDefault();
        const { email, buttonDisabled} = this.state;
        if (buttonDisabled) return; // Prevent multiple clicks
        console.log("Email: " + email);
        request(
            "POST",
            `/api/v1/auth/reset-password?email=${email}`)
            .then(
                (response) => {
                    this.setState({ active: "passReset" })
                    console.log("Reset link should be sent")
                    // setTimeout(() => {
                    //     this.setState({ buttonDisabled: false }); // Make it so password-reset can only be sent once every 5 minutes
                    // }, 5 * 60 * 1000); // 30 minutes
                })
            .catch((resetError) => {
                if (resetError.response) {
                    this.setState({ error: resetError.data });
                }
            });
    };


    onChangeHandler = (event) => {
        let name = event.target.name;
        let value = event.target.value;
        this.setState({[name]: value})
    }



    render() {
        return (
            <div className='row justify-content-center'>
                <div className='col-4'>
                    <ul className='nav nav-pills nav-justified mb-3' id='ex1' role='tablist'>
                        <li className="nav-item" role="presentation">
                            <button className={classNames("nav-link", this.state.active === "login" ? "active" : "text-white")} id="tab-login" onClick={() => this.setState({ active: "login", error: ""})}>Login</button>
                        </li>
                        <li className="nav-item" role="presentation">
                            <button className={classNames("nav-link", this.state.active === "register" ? "active" : "text-white")} id="tab-register" onClick={() => this.setState({ active: "register", error: "" })}>Register</button>
                        </li>
                    </ul>

                    <div className="tab-content">
                        <div
                            className={classNames("tab-pane", "fade", this.state.active === "login" ? "show active" : "")}
                            id="pills-login">
                            <form className="needs-validation" onSubmit={this.onSubmitLogin}>
                                <div className="form-outline mb-2">
                                    <input type="email" id="email" name="email" className="form-control"
                                           onChange={this.onChangeHandler}/>
                                    <label className="form-label text-white" htmlFor="email">Email</label>
                                </div>
                                <div className="form-outline mb-0">
                                    <input type="password" id="loginPassword" name="password" className="form-control"
                                           onChange={this.onChangeHandler}/>
                                    <label className="form-label text-white" htmlFor="loginPassword">Password</label>
                                </div>
                                {this.state.error &&
                                    <div className="error" style={{color: 'white'}}>{this.state.error}</div>}
                                <div className="form-outline mb-2">
                                    <button type="button" className="btn btn-link" style={{marginLeft: '-13px'}}
                                            onClick={() => this.setState({active: "forgotPassword"})}>Forgot Password?
                                    </button>
                                </div>
                                <button type="submit" className="btn btn-primary btn-block">Sign in</button>
                            </form>
                        </div>
                        <div
                            className={classNames("tab-pane", "fade", this.state.active === "register" ? "show active" : "")}
                            id="pills-register">
                            <form onSubmit={this.onSubmitRegister}>
                                <div className="form-outline mb-2">
                                    <input type="text" id="firstName" name="firstName" className="form-control"
                                           onChange={this.onChangeHandler}/>
                                    <label className="form-label text-white" htmlFor="firstName">First name</label>
                                </div>
                                <div className="form-outline mb-2">
                                    <input type="text" id="lastName" name="lastName" className="form-control"
                                           onChange={this.onChangeHandler}/>
                                    <label className="form-label text-white" htmlFor="lastName">Last name</label>
                                </div>
                                <div className="form-outline mb-2">
                                    <input type="email" id="email" name="email" className="form-control"
                                           onChange={this.onChangeHandler}/>
                                    <label className="form-label text-white" htmlFor="email">Email</label>
                                </div>
                                <div className="form-outline mb-2">
                                    <input type="password" id="registerPassword" name="password"
                                           className="form-control" onChange={this.onChangeHandler}/>
                                    <label className="form-label text-white" htmlFor="registerPassword">Password</label>
                                </div>
                                {this.state.error && <div className="error" style={{
                                    color: 'white',
                                    marginBottom: '15px'
                                }}>{this.state.error}</div>}
                                <button type="submit" className="btn btn-primary btn-block mb-3">Sign up</button>
                            </form>
                        </div>
                        <div
                            className={classNames("tab-pane", "fade", this.state.active === "forgotPassword" ? "show active" : "")}
                            id="pills-forgotPassword">
                            <form onSubmit={this.onSubmitForgotPassword}>
                                <div className="form-outline mb-2">
                                    <input type="email" id="email" name="email"
                                           className="form-control" onChange={this.onChangeHandler}/>
                                    <label className="form-label text-white" htmlFor="forgotPasswordEmail">Enter your
                                        email</label>
                                </div>
                                <button type="submit" className="btn btn-primary btn-block mb-4">Send Reset Email
                                </button>
                            </form>
                        </div>
                        <div
                            className={classNames("tab-pane", "fade", this.state.active === "confirmation" ? "show active" : "")}
                            id="pills-forgotPassword">
                            <p className="text-white">
                                Email verification sent to {this.state.email}
                            </p>
                            <p>
                                <button type="button" className="btn btn-link" style={{marginLeft: '-13px'}}
                                        onClick={() => this.setState({active: "login"})}>
                                    Back to Login
                                </button>
                            </p>
                        </div>
                        <div
                            className={classNames("tab-pane", "fade", this.state.active === "passReset" ? "show active" : "")}
                            id="pills-passReset">
                            <p className="text-white">
                                Password reset link has been sent to {this.state.email}
                            </p>
                            <p>
                                <button type="button" className="btn btn-link" style={{marginLeft: '-13px'}}
                                        onClick={() => this.setState({active: "login"})}>
                                    Back to Login
                                </button>
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}
