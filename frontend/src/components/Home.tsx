import "./Home.css"
import {AppUser} from "../models/AppUser.ts";

type Props = {
    user?:AppUser
}

export default function Home(props: Props) {

    return (
        <div className="home-container">
            {(props.user !== undefined) ? <h1>Welcome <br/> to the <br/> <img className="logo-container" src="/Stuffloop-2-ohne.png" alt="image not found"/>
                    {props.user.username}</h1> :
                <h1>Welcome <br/> to the
                    <br/>
                    <img className="logo-container" src="/Stuffloop-2-ohne.png" alt="image not found"/>
                    <p className="hint-container">Please sign in!</p>
                </h1>


            }
        </div>

    );
}