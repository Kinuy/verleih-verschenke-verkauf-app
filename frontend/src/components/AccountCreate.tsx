import "./Account.css"
import {useState} from "react";
import axios from "axios";
import {Link} from "react-router-dom";
import {AppUser} from "../models/AppUser.ts";

export default function AccountCreate() {

    const [accountData,setAccountData] = useState<{ username: string, password: string }>({username: "", password: ""})
    const [user, setUser] = useState<AppUser | undefined>(undefined)
    const registration = () => {
        axios.post("/api/users", {

            username: accountData.username,
            password: accountData.password

        })
            .then((element)=>{
                setUser(element.data)
            })
            .catch((error) => {
                console.error("Error registering user:", error);
            });
    }

    return (
        <div className="account-container">
            <div className="account-dialogue-container">
                <h4>Create an account</h4>
                <input value={accountData.username}
                       placeholder={"Username"}
                       onChange={(e) => setAccountData(prevState => ({...prevState, username: e.target.value}))}/>
                <input value={accountData.password}
                       placeholder={"Password"}
                       onChange={(e) => setAccountData(prevState => ({...prevState, password: e.target.value}))}/>
                {user && <p>Welcome on Board {user.username}</p>}
                <button onClick={registration}>Sign up</button>
                <h6>
                    Already have an account?
                    <Link to="/account/login" className="no-link-style">Sign in</Link>
                </h6>
            </div>
        </div>

    );
};