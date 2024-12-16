import AccountLogin from "./AccountLogin.tsx";
import {AppUser} from "../models/AppUser.ts";

type Props = {
    updateUser: (appUser: AppUser)=>void
}

export default function Account(props: Props) {

    return (
            <AccountLogin updateUser={props.updateUser}/>
    );
};