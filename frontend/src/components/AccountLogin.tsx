import "./Account.css"
import {useEffect, useState} from "react";
import {Link} from "react-router-dom";
import {AppUser} from "../models/AppUser.ts";
import {useNavigate} from "react-router";
import axiosAccess from "../utility/access.ts";

type Props = {
    updateUser: (appUser: AppUser)=>void
}

export default function AccountLogin(props: Props) {

    //const [user, setUser] = useState<{ id: string, username:string,role:string } | undefined>()

    const [user, setUser] = useState<AppUser | null | undefined>(undefined)


    const navigate = useNavigate();

    const [accountData, setAccountData] = useState<{ username: string, password: string }>({username: "", password: ""})

    const login = () => {

        axiosAccess.post("/api/users/login",undefined,{
            auth: {
                username: accountData.username,
                password: accountData.password
            }
        })
            .then((response) => {
                props.updateUser(response.data)
                setUser(response.data)
                console.log("logged in")
                navigate("/")

            })
            .catch(e=>{
                setUser(undefined)
                console.error(e)
            })
    }

    const logout = () => {
        const host = window.location.host === 'localhost:5173'
            ? 'http://localhost:8080'
            : window.location.origin

        window.open(host + "/logout", '_self')

        // axiosAccess.post("/api/users/logout")
        //     .then(()=>{
        //         console.log("Logged out successfully.")
        //         setUser(undefined)
        //         navigate("/")
        //     })
        //     .catch(e=>console.error("Logout failed",e));



        //setUser(undefined)
    }

    // const logout2 = () =>{
    //     axios.post("api/users/logout")
    //         .then(()=>console.log("logged out"))
    //         .catch(e=>console.error(e))
    //         .finally(()=>setUser(null));
    // }

    const loadCurrentUser = () => {
        axiosAccess.get("/api/users/me")
            .then((response) => {
                setUser(response.data)
            })
            .catch(e=>console.error(e));
    }

    useEffect(() => {
        loadCurrentUser()
    }, []);

    return (
        <div className="account-container">
            <div className="account-dialogue-container">
                <h4>Sign in to your account</h4>
                <input value={accountData.username}
                       placeholder={"Username"}
                       onChange={(e) => setAccountData(prevState => ({...prevState, username: e.target.value}))}/>
                <input value={accountData.password}
                       placeholder={"Password"}
                       onChange={(e) => setAccountData(prevState => ({...prevState, password: e.target.value}))}/>
                {(user === undefined) ?

                    <button className="button-container" onClick={login}>Sign in</button>
                    :
                    <div className="greeting-container">
                        {user && <p>Great to see you again {user.username}.</p>}
                        <button className="button-container" onClick={logout}>Sign out</button>
                    </div>
                }
                <h6>
                    Don't have an account?
                    <Link to="/account/register" className="no-link-style"> Create account </Link>
                </h6>
            </div>
        </div>

            );
            };