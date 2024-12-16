import {Navigate, Outlet} from "react-router-dom";
import {AppUser} from "../models/AppUser.ts";

type Props = {
    user?: AppUser;
}

export default function ProtectedRoute(props: Props){

    const isAuthenticated = props.user !== undefined;

    return(
        isAuthenticated ? <Outlet /> : <Navigate to={"/"} />
    )
}