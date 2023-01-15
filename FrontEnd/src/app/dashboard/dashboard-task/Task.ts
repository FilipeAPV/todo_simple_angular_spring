export class Task {
  title: string;
  content: string;
  done: boolean;

  constructor(title:string, content:string, done:boolean) {
    this.title = title;
    this.content = content;
    this.done = done;
  }

}
