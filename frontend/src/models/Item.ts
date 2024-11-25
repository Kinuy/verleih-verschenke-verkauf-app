import {ItemCategory} from "./ItemCategory.ts";
import {ItemStatus} from "./ItemStatus.ts";

export type Item = {
    name: string,
    img: string,
    description: string,
    category: ItemCategory,
    status: ItemStatus
}