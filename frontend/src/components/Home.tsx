import "./Home.css"
import {AppUser} from "../models/AppUser.ts";

type Props = {
    user?:AppUser
}

export default function Home(props: Props) {

    return (
        <div className="home-container">
            {(props.user !== undefined) ? <h1>Welcome <br/> to the <br/> StuffLoop <br/> {props.user.username}.</h1> :
                <h1>Welcome <br/> to the <br/> StuffLoop.<br/>
                    <br/> Please sign in!</h1>}
        </div>

    );
}